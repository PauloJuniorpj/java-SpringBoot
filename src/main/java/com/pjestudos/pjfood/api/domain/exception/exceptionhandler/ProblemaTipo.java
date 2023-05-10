package com.pjestudos.pjfood.api.domain.exception.exceptionhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ProblemaTipo {
    MENSAGEM_INCOMPRENSIVEL("/mensagem-incompreensivel","Mensagem Incompreensivel"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ERRO_NEGOCIO("/tratamento-negocio", "Erro de negocio"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");



    private String title;
    private String path;

    ProblemaTipo(String path, String title){
        this.path = "http://pjfood.com.br" + path;
        this.title = title;
    }
}
