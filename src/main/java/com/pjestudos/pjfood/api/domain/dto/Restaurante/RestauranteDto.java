package com.pjestudos.pjfood.api.domain.dto.Restaurante;

import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDto {
    private Long id;
    private String nome;
    private CozinhaDto cozinha;
    private BigDecimal taxaFrete;
    private Boolean ativo;

    public RestauranteDto(Restaurante restaurante) {
        this.id = restaurante.getId();
        this.getCozinha().setId(restaurante.getCozinha().getId());
        this.taxaFrete = restaurante.getTaxaFrete();
        this.ativo = restaurante.getAtivo();
    }
}
