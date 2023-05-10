package com.pjestudos.pjfood.api.controller;

import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.repository.CozinhaRepository;
import com.pjestudos.pjfood.api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhasId}")
    public Cozinha buscar(@PathVariable("cozinhasId") Long id){
        // refatorando e deixando mais elegante
        return cadastroCozinhaService.buscarOuFalhar(id);


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
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhasId}")
    public Cozinha atualizar(@PathVariable("cozinhasId") Long id, @RequestBody Cozinha cozinha){
        Cozinha cozinhaatual = cadastroCozinhaService.buscarOuFalhar(id);

            BeanUtils.copyProperties(cozinha, cozinhaatual, "id");
            return cadastroCozinhaService.salvar(cozinhaatual);

    }

   /* @DeleteMapping("/{cozinhasId}")
    public ResponseEntity<Cozinha> remover(@PathVariable("cozinhasId") Long id){
        //Tratando a exception da Constraint, os tratamentos foram imposto na service de cozinha
        try {
            cadastroCozinhaService.excluir(id);
            return ResponseEntity.noContent().build();

            }catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.notFound().build();

            } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
    */

    // foi usado a customização das classes de exception deixando mais elegante o codigo como pode ser visto em cima
    @DeleteMapping("/{cozinhasId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  remover(@PathVariable("cozinhasId") Long id){
            cadastroCozinhaService.excluir(id);
    }
}
