package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmar(String codigoPedido){
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }
    @Transactional
    public void cancelar(String codigoPedido) {
       var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
    }
}
