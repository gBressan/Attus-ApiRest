package com.microservico.Attus_api.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.microservico.Attus_api.Entity.Contrato;
import com.microservico.Attus_api.Entity.Evento;
import com.microservico.Attus_api.Entity.EventoEnum;
import com.microservico.Attus_api.Repository.EventoH2RepositoryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventoControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private EventoH2RepositoryTest h2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        h2Repository.deleteAll();
    }

    @Test
    public void criarEvento() {

        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/attus/eventos/");

        Contrato contrato = new Contrato();
        contrato.setId(1L);

        Evento evento = new Evento();
        evento.setTipo(EventoEnum.ASSINATURA);
        evento.setContrato(contrato);
        evento.setDataRegistro(new Date());
        evento.setDescricao("Descrição do evento");

        Evento response = restTemplate.postForObject(baseUrl, evento, Evento.class); //envia a requisição POST ao endpoint


        assertEquals("Descrição do evento", response.getDescricao()); //faz a validacap da resposta

        //verifica se persistiu no banco
        assertEquals(1, h2Repository.findAll().size());
    }
}