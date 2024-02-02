package com.pjestudos.pjfood.infrasctructure.storage.spec;

import com.pjestudos.pjfood.api.domain.service.FotoStorageService;

import java.io.InputStream;

//Assisitr as aulas envolvendo o S3 futuramento no curso
public class S3FotoStorageService implements FotoStorageService {
    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeArquivo) {

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }
}
