package com.microservico.Attus_api.Controller;

import com.microservico.Attus_api.Entity.*;
import com.microservico.Attus_api.Repository.ContratoRepository;
import com.microservico.Attus_api.Repository.ParteH2RepositoryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParteControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private ParteH2RepositoryTest parteRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        parteRepository.deleteAll();

    }

    @Test
    public void criarParteTeste() {

        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/attus/partes/");

        Contrato contrato = new Contrato();
        contrato.setId(1L);

        Parte parte = new Parte();
        parte.setContrato(contrato);
        parte.setCpfOuCnpj(111222333L);
        parte.setNome("Gabriel");
        parte.setEmail("sdfsdf@teste");
        parte.setTelefone("12123123");
        parte.setTipo(ParteEnum.ADVOGADO);


        Parte response = restTemplate.postForObject(baseUrl, parte, Parte.class);

        assertEquals(111222333L, response.getCpfOuCnpj());
        assertEquals(1, parteRepository.findAll().size());  // Verifica se a parte foi salva
    }
}