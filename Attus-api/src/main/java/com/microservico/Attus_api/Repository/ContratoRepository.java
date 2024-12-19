package com.microservico.Attus_api.Repository;

import com.microservico.Attus_api.Entity.Contrato;
import com.microservico.Attus_api.Entity.StatusContratoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByStatus(StatusContratoEnum status);

    @Query(value = "SELECT * FROM contratos WHERE DATE(data_criacao) = :dataCriacao", nativeQuery = true)
    List<Contrato> buscarPorDataCriacao(@Param("dataCriacao") LocalDate dataCriacao);

    @Query(value = "SELECT DISTINCT c.* " +
            "FROM contratos c " +
            "INNER JOIN parte p ON c.numero_contrato = p.contrato_id " +
            "WHERE p.cpf_ou_cnpj = :documento", nativeQuery = true)
    List<Contrato> buscarContratosPorCpfOuCnpj(@Param("documento") Long documento);
}
