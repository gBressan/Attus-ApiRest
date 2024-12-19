package com.microservico.Attus_api.Repository;

import com.microservico.Attus_api.Entity.Parte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParteH2RepositoryTest extends JpaRepository<Parte, Long> {
}
