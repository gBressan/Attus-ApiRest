package com.microservico.Attus_api.Controller;

import com.microservico.Attus_api.Entity.Contrato;
import com.microservico.Attus_api.Entity.Evento;
import com.microservico.Attus_api.Entity.Parte;
import com.microservico.Attus_api.Entity.StatusContratoEnum;
import com.microservico.Attus_api.Repository.ContratoH2RespositoryTest;
import com.microservico.Attus_api.Service.ContratoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContratoControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    @Autowired
    private ContratoH2RespositoryTest contratoH2RespositoryTest;

    private static RestTemplate restTemplate;

    private Long contratoId;

    private Contrato criarContratoPadrao() {
        Contrato contrato = new Contrato(
                null,
                new ArrayList<>(), // Lista de partes
                new ArrayList<>(), // Lista de eventos
                StatusContratoEnum.ATIVO,
                LocalDate.now(),
                "Contrato Padrão"
        );

        Parte parte = new Parte();
        parte.setContrato(contrato);
        contrato.getPartes().add(parte);

        Evento evento = new Evento();
        evento.setContrato(contrato);
        contrato.getEventos().add(evento);

        return contrato;
    }

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        contratoId = null;
    }

    @Test
    @Transactional
    @Rollback(false)
    public void criarContrato() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/attus/contratos/");

        Contrato contrato = criarContratoPadrao();

        Contrato response = restTemplate.postForObject(baseUrl, contrato, Contrato.class);

        assertNotNull(response, "Resposta do contrato não pode ser nula.");
        assertNotNull(response.getNumeroContrato(), "O ID do contrato não foi atribuído.");

        contratoId = response.getNumeroContrato();
        System.out.println("ID do contrato atribuído: " + contratoId);

        Optional<Contrato> contratoSalvo = contratoH2RespositoryTest.findById(contratoId);
        assertTrue(contratoSalvo.isPresent(), "Contrato não encontrado no banco de dados após criação.");
    }

    @Test
    public void listarContrato() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/attus/contratos");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<List<Contrato>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contrato>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Contrato> contratos = response.getBody();
        assertNotNull(contratos);
        assertTrue(contratos.size() > 0, "A lista de contratos está vazia.");

        boolean contratoExistente = contratos.stream()
                .anyMatch(c -> "Contrato Padrão".equals(c.getDescricao()));
        assertTrue(contratoExistente, "O contrato com a descrição 'Contrato Padrão' não foi encontrado.");
    }

    @Test
    public void salvarEConsultarContrato() {
        Contrato contrato = criarContratoPadrao();
        Contrato salvo = contratoH2RespositoryTest.save(contrato);

        assertNotNull(salvo.getNumeroContrato(), "Contrato não foi salvo corretamente.");
        Optional<Contrato> encontrado = contratoH2RespositoryTest.findById(salvo.getNumeroContrato());
        assertTrue(encontrado.isPresent(), "Contrato não foi encontrado no banco de dados.");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void deletarContrato() {
        Contrato contrato = criarContratoPadrao();
        Contrato contratoSalvo = contratoH2RespositoryTest.save(contrato);

        Long contratoId = contratoSalvo.getNumeroContrato();

        boolean existeAntesDeExcluir = contratoH2RespositoryTest.existsById(contratoId);
        assertTrue(existeAntesDeExcluir, "O contrato não foi encontrado no banco de dados.");

        contratoH2RespositoryTest.deleteById(contratoId);

        boolean existeAposExcluir = contratoH2RespositoryTest.existsById(contratoId);
        assertFalse(existeAposExcluir, "O contrato ainda existe no banco de dados após exclusão.");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void atualizarContrato() {
        String baseUrlWithPort = baseUrl.concat(":").concat(port + "").concat("/api/attus/contratos/");
        Contrato contrato = criarContratoPadrao();

        Contrato response = restTemplate.postForObject(baseUrlWithPort, contrato, Contrato.class);

        Long contratoId = response.getNumeroContrato();

        contrato.setDescricao("Contrato Atualizado via API");
        contrato.setStatus(StatusContratoEnum.SUSPENSO);
        contrato.setDataCriacao(LocalDate.now().minusDays(5));

        String updateUrl = baseUrlWithPort + contratoId;
        restTemplate.put(updateUrl, contrato);

        ResponseEntity<Contrato> getResponse = restTemplate.getForEntity(updateUrl, Contrato.class);        //verifica se o contrato foi atualizado

        Contrato contratoAtualizado = getResponse.getBody();
    }
}







