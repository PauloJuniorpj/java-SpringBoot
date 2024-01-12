package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.exception.RestauranteNaoEncontradoException;
import com.pjestudos.pjfood.api.domain.repository.CozinhaRepository;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepository;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

    public static final String MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO = "Não existe um cadastro de restaurante com código %d";
    public static final String MSG_ESTE_RESTAURANTE_JA_TA_EM_USO = "Restaurante de código %d não pode " +
            "ser removida, pois está em uso";

    private RestauranteRepository restauranteRepository;
    private CozinhaRepository cozinhaRepository;
    private CozinhaService cozinhaService;
    private CidadeService cidadeService;
    private FormaPagamentoService formaPagamentoService;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              CozinhaRepository cozinhaRepository,
                              CozinhaService cozinhaService,
                              CidadeService cidadeService,
                              FormaPagamentoService formaPagamentoService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.cozinhaService = cozinhaService;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        var cozinhaId = restaurante.getCozinha().getId();
        var cidadeId = restaurante.getEndereco().getCidade().getId();
        var cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        var cidade = cidadeService.buscarOuExceptionTratada(cidadeId);
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuTratar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException
                        (String.format(MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO,id)));
    }

    @Transactional
    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
        }catch (EmptyResultDataAccessException e){
            throw new RestauranteNaoEncontradoException(String
                    .format(MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO,restauranteId));
        }catch (
                DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String
                    .format(MSG_ESTE_RESTAURANTE_JA_TA_EM_USO,restauranteId));
        }
    }
    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuTratar(restauranteId);
        restauranteAtual.ativar();
    }
    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuTratar(restauranteId);
        restauranteAtual.inativar();
    }

    //Vincular uma forma de pagamento de um restaurante especifico
    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = buscarOuTratar(restauranteId);
        var formaDePagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.associarNovaFormaPagamento(formaDePagamento);
    }

    //Desvincular a forma de pagamanot de um restaurante especifico
    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){

        var restaurante = buscarOuTratar(restauranteId);
        var formaDePagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaDePagamento);
    }

}
