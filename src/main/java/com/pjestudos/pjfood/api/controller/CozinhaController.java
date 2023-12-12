package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.repository.CozinhaRepository;
import com.pjestudos.pjfood.api.domain.service.CadastroCozinhaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CozinhaDto> listar(){
        return toCollectionDto(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhasId}")
    public CozinhaDto buscar(@PathVariable("cozinhasId") Long id){
        // refatorando e deixando mais elegante
        return toDto(cadastroCozinhaService.buscarOuFalhar(id));


        /*Optional<Cozinha> cozinha =  cozinhaRepository.findById(id);
        //return ResponseEntity.status(HttpStatus.OK).body(cozinha);
        // jeito simplificado... de trazer um statuso 200 ok
        if(cozinha.isPresent()){
            return ResponseEntity.ok(cozinha.get());
        }
        return ResponseEntity.notFound().build();
         */
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDto adicionar(@RequestBody  CozinhaDto cozinhaDto){
        Cozinha cozinha = new Cozinha(cozinhaDto);
        return toDto(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhasId}")
    public CozinhaDto atualizar(@PathVariable("cozinhasId") Long id, @Valid @RequestBody CozinhaDto cozinhaDto){
        Cozinha cozinhaatual = cadastroCozinhaService.buscarOuFalhar(id);
            copyToDomainObject(cozinhaDto, cozinhaatual);
            return toDto(cadastroCozinhaService.salvar(cozinhaatual));
    }

    // foi usado a customização das classes de exception deixando mais elegante o codigo como pode ser visto em cima
    @DeleteMapping("/{cozinhasId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  remover(@PathVariable("cozinhasId") Long id){
            cadastroCozinhaService.excluir(id);
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
