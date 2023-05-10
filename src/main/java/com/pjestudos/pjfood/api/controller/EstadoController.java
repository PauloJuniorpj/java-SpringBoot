package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.model.Estado;
import com.pjestudos.pjfood.api.domain.repository.EstadoRepository;
import com.pjestudos.pjfood.api.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    private List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadosId}")
    public Estado buscar(@PathVariable("estadosId") Long id){
        return cadastroEstadoService.buscarOuExceptionTratada(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId,
                            @RequestBody Estado estado) {
        Estado estadoAtual = cadastroEstadoService.buscarOuExceptionTratada(estadoId);

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return cadastroEstadoService.salvar(estadoAtual);
    }

    @DeleteMapping("/{estadosId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estadosId") Long id) {
            cadastroEstadoService.excluir(id);
    }
}
