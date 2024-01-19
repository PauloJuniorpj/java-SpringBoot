package com.pjestudos.pjfood.infrasctructure.repository.spec;

import com.pjestudos.pjfood.api.domain.dto.Pedido.Filter.PedidoDtoFilter;
import com.pjestudos.pjfood.api.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

    //Query do filrtro
    public static Specification<Pedido> usandoFiltro(PedidoDtoFilter filter){
        return (pedidoRoot, query, criteriaBuilder) ->{

            //Ultilizando o fatch pra resolver o problema do N+1
            //Trazendo tudo em unico select
            if(Pedido.class.equals(query.getResultType())){
                pedidoRoot.fetch("restaurante").fetch("cozinha");
                pedidoRoot.fetch("cliente");
            }
            var predicates = new ArrayList<Predicate>();

            if(filter.getIdRestaurante() != null){
                predicates.add(criteriaBuilder.equal(pedidoRoot.get("restaurante"), filter.getIdRestaurante()));
            }
            if(filter.getIdCliente() != null){
                predicates.add(criteriaBuilder.equal(pedidoRoot.get("cliente"), filter.getIdCliente()));
            }
            if(filter.getDataCriacaoInicio() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(pedidoRoot.get("dataCriacao")
                        ,filter.getDataCriacaoInicio()));
            }
            if(filter.getDataCriacaoFim() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(pedidoRoot.get("dataCriacao")
                        ,filter.getDataCriacaoFim()));
            }
            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
