package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.repository.EstadoRepository;
import com.pjestudos.pjfood.api.domain.service.EstadoService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Listar todos os estados cadastrado")
    @GetMapping
    private List<EstadoDto> listar(){
        return toCollectionDto(estadoRepository.findAll());
    }

    @Operation(summary = "Buscar", description = "Buscar um estado especifico")
    @GetMapping("/{estadosId}")
    public EstadoDto buscar(@PathVariable("estadosId") Long id){
        return toDto(estadoService.buscarOuExceptionTratada(id));
    }

    @Operation(summary = "Cadastrar", description = "Cadastrar novo estado")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDto adicionar(@RequestBody  EstadoDto estadoDto){
        Estado estado = new Estado(estadoDto);
        return toDto(estadoService.salvar(estado));
    }

    @Operation(summary = "Atualizar", description = "Atualizar estado ja cadastrado")
    @PutMapping("/{estadoId}")
    public EstadoDto atualizar(@PathVariable Long estadoId,
                            @RequestBody @Valid EstadoDto estadoDto) {
        Estado estadoAtual = estadoService.buscarOuExceptionTratada(estadoId);
        copyToDomainObject(estadoDto, estadoAtual);
        return toDto(estadoService.salvar(estadoAtual));
    }

    @Operation(summary = "Excluir", description = "Exclusão de um estado cadastrado")
    @DeleteMapping("/{estadosId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estadosId") Long id) {
            estadoService.excluir(id);
    }

    private EstadoDto toDto(Estado estado){
        return modelMapper.map(estado, EstadoDto.class);
    }

    private List<EstadoDto> toCollectionDto(List<Estado>estados){
        return estados.stream().map(this::toDto).collect(Collectors.toList());
    }
    private void copyToDomainObject(EstadoDto estadoDto, Estado estado){
        modelMapper.map(estadoDto, estado);
    }
}
