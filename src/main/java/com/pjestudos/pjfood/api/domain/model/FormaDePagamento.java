package com.pjestudos.pjfood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//Aggregate Root
@Table(name = "forma_pagamento")
public class FormaDePagamento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

}
