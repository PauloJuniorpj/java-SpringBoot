package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.dto.Produto.FotoProduto.FotoProdutoDto;
import com.pjestudos.pjfood.api.domain.dto.Produto.FotoProduto.FotoProdutoDtoInput;
import com.pjestudos.pjfood.api.domain.exception.EntidadeNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.model.FotoProduto;
import com.pjestudos.pjfood.api.domain.service.CatalogoFotoProdutoService;
import com.pjestudos.pjfood.api.domain.service.FotoStorageService;
import com.pjestudos.pjfood.api.domain.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}")
public class FotoProdutoController {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProdutoService service;
    @Autowired
    CatalogoFotoProdutoService catalogoFotoProdutoService;
    @Autowired
    FotoStorageService fotoStorageService;

    @Operation(summary = "Atualizar Foto do Produto", description = "Atualizar a foto vinculada ao Produto")
    @PutMapping(path = "/foto-produto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                        @Valid FotoProdutoDtoInput fotoProdutoDtoInput) throws IOException {

        var produto = service.buscarOuFalhar(restauranteId, produtoId);
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

    @Operation(summary = "Buscar dados da Foto do Produto", description = "Buscar foto vinculada ao Produto")
    @GetMapping("/foto-produto")
    public FotoProdutoDto buscarFoto(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

        return toDtoFotoProduto(fotoProduto);
    }

    @Operation(summary = "Buscar Foto do Produto", description = "Buscar foto vinculada ao Produto")
    @GetMapping(value = "/foto-produto-imagem")
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept", required = false)String acceptHeader){
        try {
            var fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
            var fotoimportada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok().contentType(mediaTypeFoto).body(new InputStreamResource(fotoimportada));
        }catch(EntidadeNaoEncontradaException | HttpMediaTypeNotAcceptableException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {
        catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas)
            throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream().anyMatch(mediaTypesAceita -> mediaTypesAceita.isCompatibleWith(mediaTypeFoto));
        if(!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

    //Foto do Produto
    private FotoProdutoDto toDtoFotoProduto(FotoProduto fotoProduto){
        return modelMapper.map(fotoProduto, FotoProdutoDto.class);
    }
}
