package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.repository.CozinhaRepository;
import com.pjestudos.pjfood.api.domain.service.CozinhaService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Listar todas as cozinhas")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CozinhaDto> listar(){
        return toCollectionDto(cozinhaRepository.findAll());
    }

    @Operation(summary = "Paginação simples", description = "Listar todas as cozinhas com paginação")
    @GetMapping("/paginação")
    public Page<CozinhaDto> listarPaginada(Pageable pageable){
        Page<Cozinha> cozinhas = cozinhaRepository.findAll(pageable);
        List<CozinhaDto> cozinhaDtos = toCollectionDto(cozinhas.getContent());
        Page<CozinhaDto> cozinhaDtoPage = new PageImpl<>(cozinhaDtos, pageable, cozinhas.getTotalElements());
        return  cozinhaDtoPage;
    }

    @Operation(summary = "Buscar", description = "Buscar uma cozinha especifica")
    @GetMapping("/{cozinhasId}")
    public CozinhaDto buscar(@PathVariable("cozinhasId") Long id){
        return toDto(cozinhaService.buscarOuFalhar(id));
    }

    @Operation(summary = "Cadastrar", description = "Cadastrar nova cozinha")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDto adicionar(@RequestBody  CozinhaDto cozinhaDto){
        Cozinha cozinha = new Cozinha(cozinhaDto);
        return toDto(cozinhaService.salvar(cozinha));
    }

    @Operation(summary = "Atualizar", description = "Atualizar uma cozinha cadastrada")
    @PutMapping("/{cozinhasId}")
    public CozinhaDto atualizar(@PathVariable("cozinhasId") Long id, @Valid @RequestBody CozinhaDto cozinhaDto){
        Cozinha cozinhaatual = cozinhaService.buscarOuFalhar(id);
            copyToDomainObject(cozinhaDto, cozinhaatual);
            return toDto(cozinhaService.salvar(cozinhaatual));
    }

    @Operation(summary = "Excluir", description = "Excluzao de uma cozinha cadastrada")
    // foi usado a customização das classes de exception deixando mais elegante o codigo como pode ser visto em cima
    @DeleteMapping("/{cozinhasId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  remover(@PathVariable("cozinhasId") Long id){
            cozinhaService.excluir(id);
    }

    private CozinhaDto toDto(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaDto.class);
    }
    private List<CozinhaDto> toCollectionDto(List<Cozinha>cozinhas){
        return cozinhas.stream().map(this::toDto).collect(Collectors.toList());
    }
    private void copyToDomainObject(CozinhaDto cozinhaDto, Cozinha cozinha){
        modelMapper.map(cozinhaDto, cozinha);
    }
}
