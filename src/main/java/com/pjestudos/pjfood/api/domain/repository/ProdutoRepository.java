package com.pjestudos.pjfood.api.domain.repository;

import com.pjestudos.pjfood.api.domain.model.Produto;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);

    List<Produto> findByRestaurante(Restaurante restaurante);

    @Query("From Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);
}
