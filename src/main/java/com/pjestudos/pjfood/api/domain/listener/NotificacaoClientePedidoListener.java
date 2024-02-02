package com.pjestudos.pjfood.api.domain.listener;


import com.pjestudos.pjfood.api.domain.Event.PedidoEvent;
import com.pjestudos.pjfood.api.domain.model.Pedido;
import com.pjestudos.pjfood.api.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoEvent event){

        Pedido pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
             .assunto(pedido.getRestaurante().getNome() + "- Pedido Confirmado")
                .corpo("pedido-confirmado.html")
                 .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail()).build();
                envioEmailService.enviar(mensagem);
    }

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoEvent event){
        Pedido pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + "- Pedido Cancelado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail()).build();
        envioEmailService.enviar(mensagem);
    }
}
