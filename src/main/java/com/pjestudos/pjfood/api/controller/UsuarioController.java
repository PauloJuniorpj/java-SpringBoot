package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Grupo.GrupoDto;
import com.pjestudos.pjfood.api.domain.dto.Grupo.GrupoDtoInput;
import com.pjestudos.pjfood.api.domain.dto.Usuario.SenhaInput;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioComSenhaInput;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioDto;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioInput;
import com.pjestudos.pjfood.api.domain.model.Grupo;
import com.pjestudos.pjfood.api.domain.model.Usuario;
import com.pjestudos.pjfood.api.domain.repository.UsuarioRepository;
import com.pjestudos.pjfood.api.domain.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService cadastroUsuario;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/listar")
    public List<UsuarioDto> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();
        return toCollectionDto(todasUsuarios);
    }

    @GetMapping("/buscar/{usuarioId}")
    public UsuarioDto buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
        return toDto(usuario);
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioInput adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        var usuario = toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);
        return toDtoInput(usuario);
    }

    @PutMapping("/atualizar/{usuarioId}")
    public UsuarioInput atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
        copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
        return toDtoInput(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }

    //Mostragem de dados
    private UsuarioDto toDto(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDto.class);
    }
    private List<UsuarioDto> toCollectionDto(List<Usuario>usuarios){
        return usuarios.stream().map(this::toDto).collect(Collectors.toList());
    }

    //Inputs de dados
    private UsuarioInput toDtoInput(Usuario usuario){
        return modelMapper.map(usuario, UsuarioInput.class);
    }
    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }
    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}
