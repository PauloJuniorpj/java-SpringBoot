package com.pjestudos.pjfood.api.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1l;
    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
