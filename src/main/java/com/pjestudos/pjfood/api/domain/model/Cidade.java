package com.pjestudos.pjfood.api.domain.model;

import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDto;
import com.pjestudos.pjfood.config.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @ManyToOne// tudo que termina com ToOne vai ser Eager Loading
    @JoinColumn(name = "estado_id",nullable = false)
    private Estado estado;

    @NotBlank
    @Column(name ="nome", nullable = false)
    private String nome;

    public Cidade(CidadeDto cidadeDto) {
        id = cidadeDto.getId();
        nome = cidadeDto.getNome();
    }
}
