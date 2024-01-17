package com.pjestudos.pjfood.api.domain.dto.Pedido;

import com.pjestudos.pjfood.api.domain.dto.Endereco.EnderecoDto;
import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.dto.ItemPedido.ItemPedidoDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteIdInputDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {
    private Long id;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteIdInputDto restaurante;
    private FormaPagamentoDto formaPagamento;
    private EnderecoDto endereco;
    private List<ItemPedidoDto> itens;
}
