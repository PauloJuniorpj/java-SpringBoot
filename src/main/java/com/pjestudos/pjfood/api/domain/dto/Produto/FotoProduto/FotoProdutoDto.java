package com.pjestudos.pjfood.api.domain.dto.Produto.FotoProduto;

import com.pjestudos.pjfood.api.domain.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoProdutoDto {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
