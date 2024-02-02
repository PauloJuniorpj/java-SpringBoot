package com.pjestudos.pjfood.api.domain.Event;

import com.pjestudos.pjfood.api.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoEvent {
    private Pedido pedido;
}
