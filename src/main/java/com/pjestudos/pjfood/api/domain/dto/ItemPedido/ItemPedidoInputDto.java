package com.pjestudos.pjfood.api.domain.dto.ItemPedido;

import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoIdDto;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class ItemPedidoInputDto {

    private ProdutoIdDto produto;
    @PositiveOrZero
    private Integer quantidade;
    private String observacao;
}
