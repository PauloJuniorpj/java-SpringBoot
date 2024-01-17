package com.pjestudos.pjfood.api.domain.dto.ItemPedido;

import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoDto;
import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemPedidoDto {
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
    private ProdutoIdDto produto;
}
