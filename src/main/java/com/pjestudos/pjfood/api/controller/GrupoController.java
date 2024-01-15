package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.dto.Grupo.GrupoDto;
import com.pjestudos.pjfood.api.domain.dto.Grupo.GrupoDtoInput;
import com.pjestudos.pjfood.api.domain.model.FormaDePagamento;
import com.pjestudos.pjfood.api.domain.model.Grupo;
import com.pjestudos.pjfood.api.domain.repository.GrupoRepository;
import com.pjestudos.pjfood.api.domain.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService service;

    @Operation(summary = "Listar", description = "Listar grupo")
    @GetMapping
    public List<GrupoDto> listar() {
        var todosGrupos = grupoRepository.findAll();
        return toCollectionDto(todosGrupos);
    }

    @Operation(summary = "Buscar", description = "Buscar grupo especifico")
    @GetMapping("/{grupoId}")
    public GrupoDto buscar(@PathVariable Long grupoId) {

        return toDto(service.buscarOuFalhar(grupoId));
    }

    @Operation(summary = "Cadastrar", description = "Cadastrar um novo grupo")
    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDtoInput adicionar(@RequestBody @Valid GrupoDtoInput grupoDtoInput) {
        var grupoNovo  = new Grupo(grupoDtoInput);
        return toDtoInput(service.salvar(grupoNovo));
    }

    @Operation(summary = "Atualizar", description = "Atualizar um grupo especifico")
    @PutMapping("/atualizar/{grupoId}")
    public GrupoDtoInput atualizar(@PathVariable Long grupoId,
                                       @RequestBody @Valid GrupoDtoInput grupoDtoInput) {
        var grupoAtual = service.buscarOuFalhar(grupoId);
        copyToDomainObject(grupoDtoInput, grupoAtual);

        return toDtoInput(service.salvar(grupoAtual));
    }

    @Operation(summary = "Excluir", description = "Exclusao de um grupo")
    @DeleteMapping("/deletar/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        service.excluir(grupoId);
    }


    private GrupoDto toDto(Grupo grupo){
        return modelMapper.map(grupo, GrupoDto.class);
    }

    private GrupoDtoInput toDtoInput(Grupo grupo){
        return modelMapper.map(grupo, GrupoDtoInput.class);
    }

    private List<GrupoDto> toCollectionDto(Collection<Grupo> grupos){
        return grupos.stream().map(this::toDto).collect(Collectors.toList());
    }

    private void copyToDomainObject(GrupoDtoInput grupoDtoInput, Grupo grupo){
        modelMapper.map(grupoDtoInput, grupo);
    }
}
