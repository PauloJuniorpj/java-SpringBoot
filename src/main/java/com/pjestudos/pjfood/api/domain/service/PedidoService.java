package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.exception.PedidoNaoEncontradoException;
import com.pjestudos.pjfood.api.domain.model.Pedido;
import com.pjestudos.pjfood.api.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    private RestauranteService restauranteService;
    private CidadeService cidadeService;
    private UsuarioService usuarioService;
    private FormaPagamentoService formaPagamentoService;
    private ProdutoService produtoService;

    public PedidoService(RestauranteService restauranteService,
                         CidadeService cidadeService,
                         UsuarioService usuarioService,
                         FormaPagamentoService formaPagamentoService,
                         ProdutoService produtoService) {
        this.restauranteService = restauranteService;
        this.cidadeService = cidadeService;
        this.usuarioService = usuarioService;
        this.formaPagamentoService = formaPagamentoService;
        this.produtoService = produtoService;
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    private void validarPedido(Pedido pedido) {
        var cidade = cidadeService.buscarOuExceptionTratada(pedido.getEnderecoEntrega().getCidade().getId());
        var cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        var restaurante = restauranteService.buscarOuTratar(pedido.getRestaurante().getId());
        var formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaDePagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaDePagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            var produto = produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
