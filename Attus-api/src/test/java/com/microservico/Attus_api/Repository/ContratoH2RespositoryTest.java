package com.microservico.Attus_api.Repository;

import com.microservico.Attus_api.Entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoH2RespositoryTest extends JpaRepository<Contrato, Long> {

}
