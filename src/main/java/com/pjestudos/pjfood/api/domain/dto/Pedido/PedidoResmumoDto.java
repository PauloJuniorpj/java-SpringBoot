package com.pjestudos.pjfood.api.domain.dto.Pedido;

import com.pjestudos.pjfood.api.domain.dto.Endereco.EnderecoDto;
import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.dto.ItemPedido.ItemPedidoDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteIdInputDto;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResmumoDto {

    private String codigo;
    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private LocalDateTime dataCriacao;
    private RestauranteIdInputDto restaurante;
    private UsuarioDto cliente;

}
