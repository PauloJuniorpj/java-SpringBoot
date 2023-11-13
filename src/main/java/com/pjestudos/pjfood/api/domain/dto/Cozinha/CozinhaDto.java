package com.pjestudos.pjfood.api.domain.dto.Cozinha;

import com.pjestudos.pjfood.api.domain.model.Cozinha;
import lombok.Data;

@Data
public class CozinhaDto {
    private Long id;
    private String nome;


    public CozinhaDto(Cozinha cozinha) {
        id = cozinha.getId();
        nome = cozinha.getNome();
    }
}
