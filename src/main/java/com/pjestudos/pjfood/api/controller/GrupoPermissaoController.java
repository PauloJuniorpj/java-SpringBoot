package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.dto.Permissao.PermissaoDto;
import com.pjestudos.pjfood.api.domain.model.FormaDePagamento;
import com.pjestudos.pjfood.api.domain.model.Permissao;
import com.pjestudos.pjfood.api.domain.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grupo/{grupoId}/permissao")
public class GrupoPermissaoController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GrupoService grupoService;

    @Operation(summary = "Listar", description = "Listar permissoes vinculada a um grupo")
    @GetMapping
    public List<PermissaoDto> listar(@PathVariable Long grupoId) {
        var grupo = grupoService.buscarOuFalhar(grupoId);
        return toCollectionDto(grupo.getPermissoes());
    }

    @Operation(summary = "Associar", description = "Associar uma nova permissão a um grupo")
    @GetMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.OK)
    public void associarNovaFormaPagamento(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @Operation(summary = "Desassociar", description = "Desassociar uma forma de pagamende de um restaurante")
    //Desvincular a forma de pagamanot de um restaurante especifico
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }

    private PermissaoDto toDto(Permissao permissao){
        return modelMapper.map(permissao, PermissaoDto.class);
    }

    private List<PermissaoDto> toCollectionDto(Collection<Permissao> permissoes){
        return permissoes.stream().map(this::toDto).collect(Collectors.toList());
    }
}
