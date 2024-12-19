package com.microservico.Attus_api.Repository;

import com.microservico.Attus_api.Entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoH2RepositoryTest extends JpaRepository<Evento, Long> {
}
