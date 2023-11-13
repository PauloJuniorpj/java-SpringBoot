package com.pjestudos.pjfood.api.controller;


import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDto;
import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import com.pjestudos.pjfood.api.domain.exception.exceptionhandler.Problema;
import com.pjestudos.pjfood.api.domain.exception.EntidadeNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.EstadoNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.NegocioException;
import com.pjestudos.pjfood.api.domain.model.Cidade;
import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.repository.CidadeRepository;
import com.pjestudos.pjfood.api.domain.service.CadastroCidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CidadeDto> listar(){
        return toCollectionDto(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDto buscar(@PathVariable Long cidadeId) {
        return toDto(cadastroCidadeService.buscarOuExceptionTratada(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@RequestBody  CidadeDto cidadeDto) {
        try {
            Cidade cidade = new Cidade(cidadeDto);
            return toDto(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDto atualizar(@PathVariable  Long cidadeId,
                            @RequestBody CidadeDto cidade) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuExceptionTratada(cidadeId);
            copyToDomainObject(cidade, cidadeAtual);

            return toDto(cadastroCidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
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
