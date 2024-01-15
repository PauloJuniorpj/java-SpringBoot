package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.exception.PermissaoNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.RestauranteNaoEncontradoException;
import com.pjestudos.pjfood.api.domain.model.Permissao;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    public static final String MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO
            = "Não existe um cadastro de restaurante com código %d";

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuTratar(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException
                        (String.format(MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO,id)));
    }
}
