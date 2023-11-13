package com.pjestudos.pjfood.api.domain.dto.Restaurante;

import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteDto {
    private Long id;
    private String nome;
    private CozinhaDto cozinha;
    private BigDecimal taxaFrete;

    public RestauranteDto(Restaurante restaurante) {
        this.id = restaurante.getId();
        this.getCozinha().setId(restaurante.getCozinha().getId());
        this.taxaFrete = restaurante.getTaxaFrete();
    }
}
