package com.microservico.Attus_api.Service;

import com.microservico.Attus_api.Entity.Parte;
import com.microservico.Attus_api.Repository.ParteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParteService {

    private final ParteRepository parteRepository;

    @Autowired
    public ParteService(ParteRepository parteRepository1){
        this.parteRepository = parteRepository1;
    }

    public ResponseEntity<Object> novaParte(Parte parte){
        parteRepository.save(parte);
        return new ResponseEntity<>(parte, HttpStatus.CREATED);
    }

    public List<Parte> listarPartes(){
        return this.parteRepository.findAll();
    }

}
