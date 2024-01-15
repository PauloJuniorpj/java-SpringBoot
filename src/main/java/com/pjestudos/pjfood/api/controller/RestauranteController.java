package com.pjestudos.pjfood.api.controller;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDtoInput;
import com.pjestudos.pjfood.api.domain.exception.CidadeNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.CozinhaNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.exception.RestauranteNaoEncontradoException;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.repository.RestauranteRepository;
import com.pjestudos.pjfood.api.domain.service.RestauranteService;
import com.pjestudos.pjfood.config.core.modelmapper.modelmapperConversonsUtils.RestauranteModelMapperConversons;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

    private RestauranteRepository restauranteRepository;
    private RestauranteService restauranteService;
    private RestauranteModelMapperConversons mapperConversons;

    public RestauranteController(RestauranteRepository restauranteRepository,
                                 RestauranteService restauranteService,
                                 RestauranteModelMapperConversons mapperConversons) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteService = restauranteService;
        this.mapperConversons = mapperConversons;
    }

    @Operation(summary = "Listar", description = "Listar todos os restaurante")
    @GetMapping
    public List<RestauranteDto> listar(){
        return mapperConversons.toCollectionDto(restauranteRepository.findAll());
    }


    @Operation(summary = "Buscar", description = "Buscar um restaurante ")
    @GetMapping("/{restaurantesId}")
    public RestauranteDto buscar(@PathVariable("restaurantesId") Long id){
        Restaurante restaurante = restauranteService.buscarOuTratar(id);
        return mapperConversons.toDto(restaurante);

    }

    @Operation(summary = "Cadastrar", description = "Cadastra novo restaurante")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDtoInput adicionar(@RequestBody @Valid RestauranteDtoInput restauranteDto){
        try{
            Restaurante restaurante = mapperConversons.toDomainObject(restauranteDto);
            return mapperConversons.toDtoInput(restauranteService.salvar(restaurante));
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Operation(summary = "Atualizar", description = "Atualizar um restaurante especifico")
    @PutMapping("/{restaurantesId}")
    public RestauranteDtoInput atualizar(@PathVariable("restaurantesId") Long id,
                                                @Valid @RequestBody RestauranteDtoInput restauranteDto) {
                try{
                    Restaurante restauranteAtual = restauranteService.buscarOuTratar(id);
                    mapperConversons.copyToDomainObject(restauranteDto, restauranteAtual);
                    return mapperConversons.toDtoInput(restauranteService.salvar(restauranteAtual));
                }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
                    throw new NegocioException(e.getMessage(), e);
                }
    }

    @Operation(summary = "Excluir", description = "Excluir um restaurante especifico")
    @DeleteMapping("/{restaurantesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover
            (@PathVariable("restaurantesId")Long id) {

        restauranteService.excluir(id);
    }

    @Operation(summary = "Ativar" , description = "Ativar Restaurante")
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id){
        restauranteService.ativar(id);
    }

    @Operation(summary = "Ativar Varios" , description = "Ativar mais de um Restaurante")
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarVarios(@RequestBody List<Long> restauranteIds){
        try {
            restauranteService.ativarVarios(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Operation(summary = "Inativar", description = "Inativar Restaurante")
    @DeleteMapping("/{id}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id){
        restauranteService.inativar(id);
    }

    @Operation(summary = "Inativar Varios" , description = "Inativar mais de um Restaurante")
    @DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarVarios(@RequestBody List<Long> restauranteIds){
        try {
            restauranteService.inativarVarios(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Operation(summary = "Aberto" , description = "Abertura de restaurante")
    @PutMapping("/{id}/aberto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long id){
        restauranteService.aberto(id);
    }

    @Operation(summary = "Fechamento" , description = "Fechamento do restaurante")
    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable Long id){
        restauranteService.fechamento(id);
    }


}
