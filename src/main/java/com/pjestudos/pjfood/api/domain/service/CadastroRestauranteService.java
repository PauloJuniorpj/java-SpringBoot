package com.pjestudos.pjfood.api.domain.service;

import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDto;
import com.pjestudos.pjfood.api.domain.exception.RestauranteNaoEncontradoException;
import com.pjestudos.pjfood.api.domain.repository.CozinhaRepository;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepository;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    public static final String MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO = "Não existe um cadastro de cozinha com código %d";
    public static final String MSG_ESTE_RESTAURANTE_JA_TA_EM_USO = "Restaurante de código %d não pode " +
            "ser removida, pois está em uso";

    private RestauranteRepository restauranteRepository;
    private CozinhaRepository cozinhaRepository;
    private CadastroCozinhaService cadastroCozinhaService;

    public CadastroRestauranteService(RestauranteRepository restauranteRepository,
                                      CozinhaRepository cozinhaRepository,
                                      CadastroCozinhaService cadastroCozinhaService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.cadastroCozinhaService = cadastroCozinhaService;
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuTratar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_NAO_EXISTE_CADASTRO_COM_ESTE_CODIGO)));
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


}
