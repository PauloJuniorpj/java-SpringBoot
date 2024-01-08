package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.model.FormaDePagamento;
import com.pjestudos.pjfood.api.domain.repository.FormaDePagamentoRepository;
import com.pjestudos.pjfood.api.domain.service.FormaPagamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @Autowired
    private FormaPagamentoService service;

    @GetMapping
    public List<FormaPagamentoDto> listar() {
        List<FormaDePagamento> todasFormasPagamentos = formaDePagamentoRepository.findAll();
        return toCollectionDto(todasFormasPagamentos);
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDto buscar(@PathVariable Long formaPagamentoId) {

        return toDto(service.buscarOuFalhar(formaPagamentoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoDto formaPagamentoDto) {
        FormaDePagamento formaPagamento = new FormaDePagamento(formaPagamentoDto);
        return toDto(service.salvar(formaPagamento));
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDto atualizar(@PathVariable Long formaPagamentoId,
                                         @RequestBody @Valid FormaPagamentoDto formaPagamentoDto) {
        FormaDePagamento formaPagamentoAtual = service.buscarOuFalhar(formaPagamentoId);
        copyToDomainObject(formaPagamentoDto, formaPagamentoAtual);

        return toDto(service.salvar(formaPagamentoAtual));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        service.excluir(formaPagamentoId);
    }


    private FormaPagamentoDto toDto(FormaDePagamento formaDePagamento){
        return modelMapper.map(formaDePagamento, FormaPagamentoDto.class);
    }

    private List<FormaPagamentoDto> toCollectionDto(List<FormaDePagamento>formaDePagamentos){
        return formaDePagamentos.stream().map(this::toDto).collect(Collectors.toList());
    }

    private void copyToDomainObject(FormaPagamentoDto formaPagamentoDto, FormaDePagamento formaDePagamento){
        modelMapper.map(formaPagamentoDto, formaDePagamento);
    }
}
