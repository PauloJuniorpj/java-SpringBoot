package com.pjestudos.pjfood.api.domain.repository;

import com.pjestudos.pjfood.api.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido , Long>{
}
