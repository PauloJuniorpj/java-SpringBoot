package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.service.FluxoPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {

    @Autowired
    FluxoPedidoService pedidoService;

    @Operation(summary = "Confirmação", description = "Confirmação do pedido mudando o status dele de criado pra confirmado")
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido){
        pedidoService.confirmar(codigoPedido);
    }

    @Operation(summary = "Entregar", description = "Entregado do pedido, mudando o status dele de confirmação pra entregar")
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        pedidoService.entregar(codigoPedido);
    }

    @Operation(summary = "Cancelamento", description = "Cancelamento, mudando o status do pedido de entrega para cancelamento")
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        pedidoService.cancelar(codigoPedido);
    }
}
