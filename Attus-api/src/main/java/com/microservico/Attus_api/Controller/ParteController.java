package com.microservico.Attus_api.Controller;

import com.microservico.Attus_api.Entity.Parte;
import com.microservico.Attus_api.Service.ParteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attus/partes")
public class ParteController {

    private final ParteService parteService;

    @Autowired
    public ParteController(ParteService parteService){
        this.parteService = parteService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> criarParte(@RequestBody Parte parte){
        return parteService.novaParte(parte);
    }

    @GetMapping
    public List<Parte> listarPartes(){
        return this.parteService.listarPartes();
    }
}
