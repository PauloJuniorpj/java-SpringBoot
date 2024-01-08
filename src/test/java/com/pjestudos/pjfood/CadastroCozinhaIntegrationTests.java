package com.pjestudos.pjfood;

import com.pjestudos.pjfood.api.domain.exception.CozinhaNaoEncontradaException;
import com.pjestudos.pjfood.api.domain.exception.EntidadeEmUsoException;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.service.CozinhaService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {

	@Autowired
	private CozinhaService cozinhaService;

	// Caminho Feliz
	@Test
	public void testarCadastroCozinhaSucesso(){
		//Cenario do teste
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		//ação que a gente espera
		cozinhaService.salvar(novaCozinha);
		// e a validação do nosso teste
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}
	//Caminho infeliz
	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		DataIntegrityViolationException erroEsperado =
				Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
					cozinhaService.salvar(novaCozinha);
				});

		assertThat(erroEsperado).isNotNull();
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void cadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		novaCozinha = cozinhaService.salvar(novaCozinha);
	}

	//Caminhos infelizes
	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalharQuandoExcluirCozinhaEmUso(){
		cozinhaService.excluir(1L);
	}

	@Test(expected = CozinhaNaoEncontradaException.class)
	public void deveFalharQuandoExcluirCozinhaInexistente() {
		cozinhaService.excluir(100L);
	}
}
