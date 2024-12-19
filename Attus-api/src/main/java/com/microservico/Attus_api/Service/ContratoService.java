package com.microservico.Attus_api.Service;

import com.microservico.Attus_api.Entity.Contrato;
import com.microservico.Attus_api.Entity.Parte;
import com.microservico.Attus_api.Entity.StatusContratoEnum;
import com.microservico.Attus_api.Repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;

    @Autowired
    public ContratoService(ContratoRepository contratoRepository1){
        this.contratoRepository = contratoRepository1;
    }

    public ResponseEntity<Object> novoContrato(Contrato contrato){
        if (contrato.getPartes() != null) {
            for (Parte parte : contrato.getPartes()) {
                parte.setContrato(contrato);
            }
        }
        contratoRepository.save(contrato);
        return new ResponseEntity<>(contrato, HttpStatus.CREATED);
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> listarContratos(){
        return this.contratoRepository.findAll();
    }

    public ResponseEntity<Object> atualizarContrato(Long id, Contrato atualizarContrato){
        Optional<Contrato> contratoOptional = contratoRepository.findById(id);

        if (!contratoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Contrato contratoExistente = contratoOptional.get();

        contratoExistente.setDescricao(atualizarContrato.getDescricao());
        contratoExistente.setStatus(atualizarContrato.getStatus());
        contratoExistente.setDataCriacao(atualizarContrato.getDataCriacao());

        if (atualizarContrato.getPartes() != null) {
            contratoExistente.getPartes().clear();
            contratoExistente.getPartes().addAll(atualizarContrato.getPartes());
        }

        if (atualizarContrato.getEventos() != null) {
            contratoExistente.getEventos().clear();
            contratoExistente.getEventos().addAll(atualizarContrato.getEventos());
        }

        contratoRepository.save(contratoExistente);
        return ResponseEntity.ok(contratoExistente);
    }

    public ResponseEntity<Object> deletarContrato(Long id) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(id);

        if (!contratoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (contratoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato não encontrado.");
        }

        contratoRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }

    public List<Contrato> buscarContratoPorStatus(StatusContratoEnum status){
        return contratoRepository.findByStatus(status);
    }

    public List<Contrato> buscarPorData(LocalDate dataCriacao){
        return contratoRepository.buscarPorDataCriacao(dataCriacao);
    }
    public List<Contrato> buscarPorCpfOuCnpj(Long documento) {
        return contratoRepository.buscarContratosPorCpfOuCnpj(documento);
    }

}
