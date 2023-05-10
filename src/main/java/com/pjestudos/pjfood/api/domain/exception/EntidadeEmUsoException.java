package com.pjestudos.pjfood.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class EntidadeEmUsoException extends RuntimeException{

    private static final long serialVersionUID = 1l;

    public EntidadeEmUsoException(String mensagem){
        super(mensagem);
    }
}
