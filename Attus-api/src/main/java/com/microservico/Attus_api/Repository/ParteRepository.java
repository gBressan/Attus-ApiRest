package com.microservico.Attus_api.Repository;

import com.microservico.Attus_api.Entity.Parte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParteRepository extends JpaRepository<Parte, Long> {
}
