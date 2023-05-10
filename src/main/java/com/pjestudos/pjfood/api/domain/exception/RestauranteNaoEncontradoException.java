package com.pjestudos.pjfood.api.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1l;
    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
