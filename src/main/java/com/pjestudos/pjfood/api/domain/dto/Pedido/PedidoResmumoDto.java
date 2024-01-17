package com.pjestudos.pjfood.api.domain.dto.Pedido;

import com.pjestudos.pjfood.api.domain.dto.Endereco.EnderecoDto;
import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.dto.ItemPedido.ItemPedidoDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteIdInputDto;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoResmumoDto {
    private Long id;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteIdInputDto restaurante;
    private UsuarioDto usuario;

}
