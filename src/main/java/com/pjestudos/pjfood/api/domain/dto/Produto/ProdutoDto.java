package com.pjestudos.pjfood.api.domain.dto.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private boolean ativo;
}
