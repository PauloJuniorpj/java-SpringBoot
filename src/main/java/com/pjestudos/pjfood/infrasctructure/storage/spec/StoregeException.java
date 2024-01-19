package com.pjestudos.pjfood.infrasctructure.storage.spec;

public class StoregeException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public StoregeException(String message) {
        super(message);
    }

    public StoregeException(String message, Throwable cause) {
        super(message, cause);
    }
}
