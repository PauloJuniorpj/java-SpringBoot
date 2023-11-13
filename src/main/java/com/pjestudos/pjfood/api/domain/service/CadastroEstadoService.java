package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.exception.EstadoNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.repository.EstadoRepository;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode " +
            "ser removida, pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado buscarOuExceptionTratada(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(()-> new EstadoNaoEncontradaException(id));
    }


    @Transactional
    public void excluir(Long id) {
        try{
            estadoRepository.deleteById(id);

        }catch (EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradaException((id));
        }
        catch (
                DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String
                    .format(MSG_ESTADO_EM_USO,id));
        }
    }


}
