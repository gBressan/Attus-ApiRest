package com.microservico.Attus_api.Controller;

import com.microservico.Attus_api.Entity.Contrato;
import com.microservico.Attus_api.Entity.StatusContratoEnum;
import com.microservico.Attus_api.Service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attus/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    @Autowired
    public ContratoController(ContratoService contratoService){
        this.contratoService = contratoService;
    }

    @GetMapping
    public List<Contrato> listarContratos(){
        return this.contratoService.listarContratos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> obterContrato(@PathVariable Long id) {
        Optional<Contrato> contrato = contratoService.buscarPorId(id);

        if (contrato.isPresent()) {
            return ResponseEntity.ok(contrato.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> criarContrato(@RequestBody Contrato contrato){
        ResponseEntity<Object> contratoSalvo = contratoService.novoContrato(contrato);
        return contratoService.novoContrato(contrato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarContratoById(@PathVariable Long id, @RequestBody Contrato atualizarContrato){
        return this.contratoService.atualizarContrato(id,atualizarContrato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarContratoById(@PathVariable("id") Long id){
        return this.contratoService.deletarContrato(id);
    }

    //filtros

    //?status=X
    @GetMapping("/status")
    public ResponseEntity<List<Contrato>> buscarPorStatus(@RequestParam("status") StatusContratoEnum status) {
        List<Contrato> contratos = contratoService.buscarContratoPorStatus(status);
        return ResponseEntity.ok(contratos);

    }

    //?dataCriacao=yyyy-MM-dd
    @GetMapping("/data")
    public ResponseEntity<List<Contrato>> buscaPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCriacao){
        List<Contrato> contratos = contratoService.buscarPorData(dataCriacao);
        return ResponseEntity.ok(contratos);
    }

    //?documento=X
    @GetMapping("/documento")
    public ResponseEntity<List<Contrato>> buscarPorCpfOuCnpj(@RequestParam Long documento) {
        List<Contrato> contratos = contratoService.buscarPorCpfOuCnpj(documento);
        return ResponseEntity.ok(contratos);
    }

}
