package com.pjestudos.pjfood.infrasctructure.service.spec.query;

import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiaria;
import com.pjestudos.pjfood.api.domain.dto.VendaDiaria.VendaDiariaFilter;
import com.pjestudos.pjfood.api.domain.model.Pedido;
import com.pjestudos.pjfood.api.domain.model.StatusPedido;
import com.pjestudos.pjfood.api.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Predicate;
import javax.xml.crypto.Data;

@Repository
public class VendaQuerySpecImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    //CriteriaBuilder
    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var pedidoRoot = query.from(Pedido.class);

        //Logica do filtro
        var predicates = new ArrayList<Predicate>();

        if (vendaDiariaFilter.getRestauranteId() != null) {
            predicates.add(builder.equal(pedidoRoot.get("restaurante"), vendaDiariaFilter.getRestauranteId()));
        }

        if (vendaDiariaFilter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(pedidoRoot.get("dataCriacao"),
                    vendaDiariaFilter.getDataCriacaoInicio()));
        }

        if (vendaDiariaFilter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(pedidoRoot.get("dataCriacao"),
                    vendaDiariaFilter.getDataCriacaoFim()));
        }

        predicates.add(pedidoRoot.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
        //Logica do filtro

        var functionConvertTzDataCriacao = builder.function("convert_tz",
                Data.class, pedidoRoot.get("dataCriacao"),
                builder.literal("+00:00"), builder.literal(timeOffSet));

        var functionDateDataCriacao = builder.function("date", Date.class,
                functionConvertTzDataCriacao);

        var selection =

                builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(pedidoRoot.get("id")),
                builder.sum(pedidoRoot.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }
}
