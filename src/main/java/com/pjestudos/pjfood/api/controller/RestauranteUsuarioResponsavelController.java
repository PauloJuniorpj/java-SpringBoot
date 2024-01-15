package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Permissao.PermissaoDto;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioDto;
import com.pjestudos.pjfood.api.domain.model.Permissao;
import com.pjestudos.pjfood.api.domain.model.Usuario;
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
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {
    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Listar usuários vinculados a restaurantes")
    @GetMapping
    public List<UsuarioDto> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuTratar(restauranteId);
        return toCollectionDto(restaurante.getResponsaveis());
    }

    @Operation(summary = "Desassociar", description = "Desassociar uma usuário de um restaurante")
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }

    @Operation(summary = "Associar", description = "Associar uma usuário a um restaurante")
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    private UsuarioDto toDto(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    private List<UsuarioDto> toCollectionDto(Collection<Usuario> usuarios){
        return usuarios.stream().map(this::toDto).collect(Collectors.toList());
    }
}

