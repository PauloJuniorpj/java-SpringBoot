package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.model.Cidade;
import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.repository.CidadeRepository;
import com.pjestudos.pjfood.api.domain.repository.EstadoRepository;
import com.pjestudos.pjfood.api.domain.exception.CidadeNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    private CadastroEstadoService cadastroEstadoService;

    public CadastroCidadeService(CadastroEstadoService cadastroEstadoService) {
        this.cadastroEstadoService = cadastroEstadoService;
    }

    public static final String MSG_CIDADE_NAO_ENCONTRADO = "Não existe cadastro de " +
            "cidade com código %d";
    public static final String MSG_CIDADE_JA_EM_USO = "Cidade de código %d não pode " +
            "ser removida, pois está em uso";


    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscarOuExceptionTratada(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public Cidade buscarOuExceptionTratada(Long id){
        return cidadeRepository.findById(id).orElseThrow(() ->
                new CidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADO, id)));
    }

    @Transactional
    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException(String
                    .format(MSG_CIDADE_NAO_ENCONTRADO,id));
        }catch (
                DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String
                    .format(MSG_CIDADE_JA_EM_USO,id));
        }
    }


}
