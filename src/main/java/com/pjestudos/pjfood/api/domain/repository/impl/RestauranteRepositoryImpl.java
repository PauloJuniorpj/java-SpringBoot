package com.pjestudos.pjfood.api.domain.repository.impl;


import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepository;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.pjestudos.pjfood.infrasctructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.pjestudos.pjfood.infrasctructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        //  Exemplo de consultar mais dinamica usando Criteria
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        var root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasLength(nome)){
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }
        if (taxaFreteInicial != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if (taxaFreteFinal != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }
        //Automaticamente ele ta fazendo o and em cada um da lista
        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = (TypedQuery<Restaurante>) manager.createQuery(criteria).getResultList();
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return  restauranteRepository.findAll(comFreteGratis()
                .and(comNomeSemelhante(nome)));
    }
    /*
    consulta com JPQL Dinamica
        var jpql = new StringBuilder();
        var parametros = new HashMap<String, Object>();

        jpql.append("from Restaurante where 0 = 0 ");
        if(StringUtils.hasLength(nome)){
            jpql.append("where nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }
        if (taxaFreteInicial != null){
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", "%" + taxaFreteInicial + "%");
        }
        if (taxaFreteFinal != null){
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", "%" + taxaFreteFinal + "%");
        }


        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

            parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
            return query.getResultList();

         */
}
