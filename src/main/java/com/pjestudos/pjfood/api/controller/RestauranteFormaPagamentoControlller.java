package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.model.FormaDePagamento;
import com.pjestudos.pjfood.api.domain.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoControlller {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Listar forma de pagamento vinculada a um restaurante")
    @GetMapping
    public List<FormaPagamentoDto> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuTratar(restauranteId);
        return toCollectionDto(restaurante.getFormasPagamento());
    }

    @Operation(summary = "Associar", description = "Associar uma nova forma de pagamento a um restaurante")
    @GetMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.OK)
    public void associarNovaFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @Operation(summary = "Desassociar", description = "Desassociar uma forma de pagamende de um restaurante")
    //Desvincular a forma de pagamanot de um restaurante especifico
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    private FormaPagamentoDto toDto(FormaDePagamento formaDePagamento){
        return modelMapper.map(formaDePagamento, FormaPagamentoDto.class);
    }

    private List<FormaPagamentoDto> toCollectionDto(Collection<FormaDePagamento> formaDePagamentos){
        return formaDePagamentos.stream().map(this::toDto).collect(Collectors.toList());
    }
}
