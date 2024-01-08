package com.pjestudos.pjfood.api.controller;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDto;
import com.pjestudos.pjfood.api.domain.exception.CozinhaNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepository;
import com.pjestudos.pjfood.api.domain.service.RestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<RestauranteDto> listar(){
        return toCollectionDto(restauranteRepository.findAll());
    }

    @GetMapping("/{restaurantesId}")
    public RestauranteDto buscar(@PathVariable("restaurantesId") Long id){
        Restaurante restaurante = restauranteService.buscarOuTratar(id);
        return toDto(restaurante);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody @Valid RestauranteDto restauranteDto){
        try{
            Restaurante restaurante = new Restaurante(restauranteDto);
            return toDto(restauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException e ){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restaurantesId}")
    public RestauranteDto atualizar(@PathVariable("restaurantesId") Long id,
                                                @Valid @RequestBody RestauranteDto restauranteDto) {
                try{
                    Restaurante restauranteAtual = restauranteService.buscarOuTratar(id);
                   copyToDomainObject(restauranteDto, restauranteAtual);
                    return toDto(restauranteService.salvar(restauranteAtual));
                }catch (CozinhaNaoEncontradaException e){
                    throw new NegocioException(e.getMessage(), e);
                }
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

        restauranteService.excluir(id);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id){
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id){
        restauranteService.inativar(id);
    }

    private RestauranteDto toDto(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteDto.class);
    }

    private List<RestauranteDto> toCollectionDto(List<Restaurante>restaurantes){
        return restaurantes.stream().map(this::toDto).collect(Collectors.toList());
    }

    private void copyToDomainObject(RestauranteDto restauranteDto, Restaurante restaurante){
        //Para evitar expetion identifier of an istance of.Cozinha as altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteDto, restaurante);
    }
}
