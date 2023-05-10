package com.pjestudos.pjfood.api.domain.repository;


import com.pjestudos.pjfood.api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor {

    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);

    // ****** OBS ******
    // maneiras de concerta o problema do featch lazy nos ManyToOne que ja sao padrao eager
    @Query("from Restaurante r join fetch  r.cozinha")
    List<Restaurante> findAll();
    // ****** OBS ******
    // se um restaurante não tiver nenhuma forma de pagamento associada a ele esse restaurante
    //não será retornado.Para resolver isso, temos que usar o LEFT JOIN FETCH, no lugar de JOIN FETCH.
}
