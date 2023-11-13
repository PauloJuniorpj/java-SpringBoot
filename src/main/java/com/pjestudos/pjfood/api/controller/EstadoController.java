package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Cozinha.CozinhaDto;
import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.repository.EstadoRepository;
import com.pjestudos.pjfood.api.domain.service.CadastroEstadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private List<EstadoDto> listar(){
        return toCollectionDto(estadoRepository.findAll());
    }

    @GetMapping("/{estadosId}")
    public EstadoDto buscar(@PathVariable("estadosId") Long id){
        return toDto(cadastroEstadoService.buscarOuExceptionTratada(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDto adicionar(@RequestBody  EstadoDto estadoDto){
        Estado estado = new Estado(estadoDto);
        return toDto(cadastroEstadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoDto atualizar(@PathVariable Long estadoId,
                            @RequestBody @Valid EstadoDto estadoDto) {
        Estado estadoAtual = cadastroEstadoService.buscarOuExceptionTratada(estadoId);
        copyToDomainObject(estadoDto, estadoAtual);
        return toDto(cadastroEstadoService.salvar(estadoAtual));
    }

    @DeleteMapping("/{estadosId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estadosId") Long id) {
            cadastroEstadoService.excluir(id);
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
