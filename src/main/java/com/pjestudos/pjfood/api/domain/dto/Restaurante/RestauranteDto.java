package com.pjestudos.pjfood.api.domain.dto.Restaurante;

import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.api.domain.dto.Endereco.EnderecoDto;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDto {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private CozinhaDto cozinha;
    private EnderecoDto endereco;

}
