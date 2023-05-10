package com.pjestudos.pjfood.api.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
//Aggregate Root
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne// tudo que termina com ToOne vai ser Eager Loading
    @JoinColumn(name = "estado_id",nullable = false)
    private Estado estado;

    @Column(name ="nome", nullable = false)
    private String nome;
}
