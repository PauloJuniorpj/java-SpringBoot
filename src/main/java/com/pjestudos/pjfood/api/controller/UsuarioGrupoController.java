package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Grupo.GrupoDto;
import com.pjestudos.pjfood.api.domain.model.Grupo;
import com.pjestudos.pjfood.api.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario/{usuarioId}/grupo")
public class UsuarioGrupoController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar", description = "Listar permissoes vinculada a um grupo")
    @GetMapping
    public List<GrupoDto> listar(@PathVariable Long usuarioId) {
        var usuario = usuarioService.buscarOuFalhar(usuarioId);
        return toCollectionDto(usuario.getGrupos());
    }

    @Operation(summary = "Associar", description = "Associar um grupo a um usuário")
    @GetMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.OK)
    public void associarNovoGrupoAUmUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

    @Operation(summary = "Desassociar", description = "Desassociar uma grupo de um usuário")
    //Desvincular a forma de pagamanot de um restaurante especifico
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupoAUmUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }

    private GrupoDto toDto(Grupo grupo){
        return modelMapper.map(grupo, GrupoDto.class);
    }
    private List<GrupoDto> toCollectionDto(Collection<Grupo> grupos){
        return grupos.stream().map(this::toDto).collect(Collectors.toList());
    }
}
