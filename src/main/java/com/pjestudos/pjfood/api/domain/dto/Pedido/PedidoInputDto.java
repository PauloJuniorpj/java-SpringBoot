package com.pjestudos.pjfood.api.domain.dto.Pedido;

import com.pjestudos.pjfood.api.domain.dto.Endereco.EnderecoDtoInput;
import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoIdInput;
import com.pjestudos.pjfood.api.domain.dto.ItemPedido.ItemPedidoInputDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteIdInputDto;
import lombok.Data;

import java.util.List;

@Data
public class PedidoInputDto {
    private RestauranteIdInputDto restaurante;
    private EnderecoDtoInput endereco;
    private FormaPagamentoIdInput formaPagamento;
    private List<ItemPedidoInputDto> itens;
}
