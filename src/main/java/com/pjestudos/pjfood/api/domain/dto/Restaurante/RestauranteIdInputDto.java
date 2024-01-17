package com.pjestudos.pjfood.api.domain.dto.Restaurante;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestauranteIdInputDto {
    @NotNull
    private Long id;
    private String nome;
}
