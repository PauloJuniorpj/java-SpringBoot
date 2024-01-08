package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.repository.CozinhaRepository;
import com.pjestudos.pjfood.api.domain.exception.CozinhaNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaService {

    public static final String MSG_COZINHA_JA_EM_USO = "Cozinha de código %d não pode " +
            "ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha buscarOuFalhar(Long cozinhaId){
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }


    @Transactional
    public void excluir(Long cozinhaId){
        try{
            cozinhaRepository.deleteById(cozinhaId);

        }catch (EmptyResultDataAccessException e){
            throw new CozinhaNaoEncontradaException(cozinhaId);
        }
        catch (
        DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String
                    .format(MSG_COZINHA_JA_EM_USO,cozinhaId));
        }
    }



}
