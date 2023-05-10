package com.pjestudos.pjfood.api.controller;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjestudos.pjfood.api.domain.exception.CozinhaNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepository;
import com.pjestudos.pjfood.api.domain.service.CadastroRestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restaurantesId}")
    public Restaurante buscar(@PathVariable("restaurantesId") Long id){
        return cadastroRestauranteService.buscarOuTratar(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante){
        try{
            return cadastroRestauranteService.salvar(restaurante);
        }catch (CozinhaNaoEncontradaException e ){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restaurantesId}")
    public Restaurante atualizar(@PathVariable("restaurantesId") Long id,
                                                 @RequestBody Restaurante restaurante) {
                try{
                    Restaurante restauranteAtual = cadastroRestauranteService.buscarOuTratar(id);
                    BeanUtils.copyProperties(restaurante, restauranteAtual, "id","formaDePagamentos",
                            "endereco", "dataCadastro");
                    return cadastroRestauranteService.salvar(restauranteAtual);
                }catch (CozinhaNaoEncontradaException e){
                    throw new NegocioException(e.getMessage(), e);
                }
    }

    @PatchMapping("/{restaurantesId}")
    public Restaurante atualizarParcial(@PathVariable("restaurantesId") Long id,
                                        @RequestBody Map<String, Object> campos, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuTratar(id);
        merge(campos, restauranteAtual, request);
        return atualizar(id, restauranteAtual);
    }

    // esse metodo serve pra mesclar as propriedades com as atuais
    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                //System.out.println(nomePropriedade + " = " + valorPropriedade);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        }catch (IllegalArgumentException e){
            Throwable causaRaiz = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), causaRaiz, servletServerHttpRequest);
        }
    }

    @DeleteMapping("/{restaurantesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover
            (@PathVariable("restaurantesId")Long id) {

        cadastroRestauranteService.excluir(id);
    }

}
