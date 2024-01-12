package com.pjestudos.pjfood.api.domain.dto.Produto;

import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteIdInputDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoInput {
    private String nome;
    private String preco;
    private boolean ativo;

}
