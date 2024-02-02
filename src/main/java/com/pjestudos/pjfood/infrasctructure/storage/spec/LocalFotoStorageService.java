package com.pjestudos.pjfood.infrasctructure.storage.spec;

import com.pjestudos.pjfood.api.domain.service.FotoStorageService;
import com.pjestudos.pjfood.config.core.Storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("{$pjfood.storage.local.diretorio-fotos}")
    private Path diretoriosFotos;

    /* @Autowired
    private StorageProperties storageProperties;

     */

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getAqruivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StoregeException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getAqruivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StoregeException("Não foi possivel excluir este arquivo", e);
        }
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getAqruivoPath(nomeArquivo);
           return Files.newInputStream(arquivoPath);
        } catch (Exception e) {
            throw new StoregeException("Não foi possível recuperar arquivo.", e);
        }
    }

    private Path getAqruivoPath(String nomeArquivo){
        return diretoriosFotos.relativize(Path.of(nomeArquivo));
    }
}
