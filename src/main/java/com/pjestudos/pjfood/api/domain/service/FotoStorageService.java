package com.pjestudos.pjfood.api.domain.service;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {
    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);
    InputStream recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal){
        return UUID.randomUUID().toString() + "_" +nomeOriginal;
    }

    default void subistituir(String nomeArquivoAntigo, NovaFoto novaFoto){
        this.armazenar(novaFoto);
        if(nomeArquivoAntigo != null){
            this.remover(nomeArquivoAntigo);
        }
    }


    @Builder
    @Data
    class NovaFoto{
        private String nomeArquivo;
        private InputStream inputStream;

    }
}
