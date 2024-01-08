package com.pjestudos.pjfood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
//Aggregate Root
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

    public Cozinha(CozinhaDto cozinhaDto) {
        id = cozinhaDto.getId();
        nome = cozinhaDto.getNome();
    }
}
