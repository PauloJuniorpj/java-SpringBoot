package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import com.pjestudos.pjfood.api.domain.exception.FormaPagamentoNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.model.FormaDePagamento;
import com.pjestudos.pjfood.api.domain.repository.FormaDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO
            = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    @Autowired
    private FormaDePagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaDePagamento salvar(FormaDePagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaDePagamento buscarOuFalhar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }
    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
        }
    }
}
