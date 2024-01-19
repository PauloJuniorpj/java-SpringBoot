package com.pjestudos.pjfood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaDePagamento formaDePagamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @ManyToOne
    @JoinColumn(name = "restaurante_id",nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @JsonIgnore
    @Embedded
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    //Gerar o UUID
    //methodo de callback do JPA ele vai executar antes de eu persistir um novo dado
    @PrePersist
    private void gerarUUID(){
        setCodigo(UUID.randomUUID().toString());
    }

    //RN Mudando Status do Pedido
    public void confirmar(){
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }
    public void entregar(){
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }
    public void cancelar(){
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    private void setStatus(StatusPedido novoStatus){
        if(getStatus().naoPodeAlterarPara(novoStatus)){
            throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
                    getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
        }
        this.status = novoStatus;
    }

}
