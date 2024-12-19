package com.microservico.Attus_api.Service;

import com.microservico.Attus_api.Entity.Evento;
import com.microservico.Attus_api.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository1){
        this.eventoRepository = eventoRepository1;
    }

    public ResponseEntity<Object> novoEvento(Evento evento){
        eventoRepository.save(evento);
        return new ResponseEntity<>(evento, HttpStatus.CREATED);
    }

}
