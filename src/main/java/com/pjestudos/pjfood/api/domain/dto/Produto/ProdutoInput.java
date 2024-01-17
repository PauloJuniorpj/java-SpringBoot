package com.pjestudos.pjfood.api.domain.dto.Produto;


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
