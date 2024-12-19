package com.microservico.Attus_api.Controller;

import com.microservico.Attus_api.Entity.Evento;
import com.microservico.Attus_api.Service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/attus/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService){
        this.eventoService = eventoService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> criarEvento(@RequestBody Evento evento){
        return eventoService.novoEvento(evento);
    }

}
