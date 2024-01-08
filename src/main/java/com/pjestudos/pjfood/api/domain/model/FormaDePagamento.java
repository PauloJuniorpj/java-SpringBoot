package com.pjestudos.pjfood.api.domain.model;

import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//Aggregate Root
@Table(name = "forma_pagamento")
@AllArgsConstructor
@NoArgsConstructor
public class FormaDePagamento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    public FormaDePagamento(FormaPagamentoDto formaPagamentoDto) {
       this.id = formaPagamentoDto.getId();
       this.descricao = formaPagamentoDto.getDescricao();
    }
}
