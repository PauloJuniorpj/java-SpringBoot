package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.model.FotoProduto;
import com.pjestudos.pjfood.api.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

import static com.pjestudos.pjfood.api.domain.service.FotoStorageService.*;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosDoArquivo){
        var restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        var produtoId = fotoProduto.getProduto().getId();
        var nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        var foto = produtoRepository.findFotoById(restauranteId, produtoId);


        //RN sempre que a gente cadastra uma foto se ela ja existe a gente exclui
        foto.ifPresent(fotoExistente -> produtoRepository.delete(fotoExistente));

        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        var fotoSalvaDados = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        var novaFotoImagema = NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosDoArquivo)
                .build();

        fotoStorageService.armazenar(novaFotoImagema);

        return fotoSalvaDados;

    }
}
