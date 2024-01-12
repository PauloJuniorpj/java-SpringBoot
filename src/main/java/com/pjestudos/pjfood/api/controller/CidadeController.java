package com.pjestudos.pjfood.api.controller;


import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDto;
import com.pjestudos.pjfood.api.domain.exception.EstadoNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.model.Cidade;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import com.pjestudos.pjfood.api.domain.repository.CidadeRepository;
import com.pjestudos.pjfood.api.domain.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Retorna todas as cozinhas")
    @GetMapping
    public List<CidadeDto> listar(){
        return toCollectionDto(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDto buscar(@PathVariable Long cidadeId) {
        return toDto(cidadeService.buscarOuExceptionTratada(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@RequestBody  CidadeDto cidadeDto) {
        try {
            Cidade cidade = toDomainObject(cidadeDto);
            return toDto(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDto atualizar(@PathVariable  Long cidadeId,
                            @RequestBody CidadeDto cidade) {
        try {
            Cidade cidadeAtual = cidadeService.buscarOuExceptionTratada(cidadeId);
            copyToDomainObject(cidade, cidadeAtual);

            return toDto(cidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }


    private Cidade toDomainObject(CidadeDto cidadeDto){
        return modelMapper.map(cidadeDto, Cidade.class);
    }
    private CidadeDto toDto(Cidade cidade){
        return modelMapper.map(cidade, CidadeDto.class);
    }
    private List<CidadeDto> toCollectionDto(List<Cidade>cidades){
        return cidades.stream().map(this::toDto).collect(Collectors.toList());
    }
    private void copyToDomainObject(CidadeDto cidadeDto, Cidade cidade){
        modelMapper.map(cidadeDto, cidade);
    }
}
