package com.pjestudos.pjfood.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
//Aggregate Root
@Table(name = "permissao")
@AllArgsConstructor
@NoArgsConstructor
public class Permissao {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "descricao", nullable = false)
    private String descricao;
}
