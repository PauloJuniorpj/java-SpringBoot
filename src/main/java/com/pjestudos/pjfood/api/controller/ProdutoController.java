package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.FormaPagamento.FormaPagamentoDto;
import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoDto;
import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoInput;
import com.pjestudos.pjfood.api.domain.dto.Usuario.UsuarioInput;
import com.pjestudos.pjfood.api.domain.model.FormaDePagamento;
import com.pjestudos.pjfood.api.domain.model.Produto;
import com.pjestudos.pjfood.api.domain.model.Usuario;
import com.pjestudos.pjfood.api.domain.repository.ProdutoRepository;
import com.pjestudos.pjfood.api.domain.service.ProdutoService;
import com.pjestudos.pjfood.api.domain.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class ProdutoController {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ProdutoService service;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Listar", description = "Listar produtos do restaurante")
    @GetMapping
    public List<ProdutoDto> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuTratar(restauranteId);
        return toCollectionDto(restaurante.getProdutos());
    }

    @Operation(summary = "Buscar", description = "Buscar produto especifico")
    @GetMapping("/{produtoId}")
    public ProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = service.buscarOuFalhar(restauranteId, produtoId);
        return toDto(produto);
    }
    @Operation(summary = "Cadastrar", description = "Cadastrar novo produto ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        var restaurante = restauranteService.buscarOuTratar(restauranteId);
        var produto = toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = service.salvar(produto);
        return toDto(produto);
    }

    @Operation(summary = "Atualizar", description = "Atualizar um produto ja cadastrado")
    @PutMapping("/{produtoId}")
    public ProdutoDto atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        var produtoAtual = service.buscarOuFalhar(restauranteId, produtoId);
        copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = service.salvar(produtoAtual);
        return toDto(produtoAtual);
    }

    //Visualização
    private ProdutoDto toDto(Produto produto){
        return modelMapper.map(produto, ProdutoDto.class);
    }
    private List<ProdutoDto> toCollectionDto(List<Produto> produtos){
        return produtos.stream().map(this::toDto).collect(Collectors.toList());
    }

    //Input Produto
    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }
    private void copyToDomainObject(ProdutoInput produtoInput, Produto produto){
        modelMapper.map(produtoInput, produto);
    }
}
