package com.pjestudos.pjfood.infrasctructure.service.spec.relatorio;

public class ReportExeption extends RuntimeException{

    private static  final long serialVersionUID= 1L;

    public ReportExeption(String message) {
        super(message);
    }

    public ReportExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
