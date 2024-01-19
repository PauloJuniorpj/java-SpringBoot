package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Produto.FotoProduto.FotoProdutoDto;
import com.pjestudos.pjfood.api.domain.dto.Produto.FotoProduto.FotoProdutoDtoInput;
import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoDto;
import com.pjestudos.pjfood.api.domain.dto.Produto.ProdutoInput;
import com.pjestudos.pjfood.api.domain.model.FotoProduto;
import com.pjestudos.pjfood.api.domain.model.Produto;
import com.pjestudos.pjfood.api.domain.repository.ProdutoRepository;
import com.pjestudos.pjfood.api.domain.service.CatalogoFotoProdutoService;
import com.pjestudos.pjfood.api.domain.service.ProdutoService;
import com.pjestudos.pjfood.api.domain.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ProdutoService service;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Operation(summary = "Listar", description = "Listar produtos do restaurante")
    @GetMapping
    public List<ProdutoDto> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
        var restaurante = restauranteService.buscarOuTratar(restauranteId);
        List<Produto> produtosRestaurante;
        if(incluirInativos){
            produtosRestaurante = produtoRepository.findByRestaurante(restaurante);
        }else{
           produtosRestaurante = produtoRepository.findAtivosByRestaurante(restaurante);
        }
        return toCollectionDto(produtosRestaurante);
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

    @Operation(summary = "Atualizar Foto do Produto", description = "Atualizar a foto vinculada ao Produto")
    @PutMapping(path = "/{produtoiD}/foto-produto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoiD,
                                        @Valid FotoProdutoDtoInput fotoProdutoDtoInput) throws IOException {

        var produto = service.buscarOuFalhar(restauranteId, produtoiD);
        var arquivo = fotoProdutoDtoInput.getArquivo();

        var fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoDtoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        var fotoSalva = catalogoFotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());
        return toDtoFotoProduto(fotoSalva);
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

    //Foto do Produto
    private FotoProdutoDto toDtoFotoProduto(FotoProduto fotoProduto){
        return modelMapper.map(fotoProduto, FotoProdutoDto.class);
    }
}
