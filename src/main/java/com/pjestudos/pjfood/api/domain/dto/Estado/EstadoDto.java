package com.pjestudos.pjfood.api.domain.dto.Estado;

import com.pjestudos.pjfood.api.domain.model.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadoDto {
    private  Long id;
    private String nome;



    public EstadoDto(Estado estado) {
        this.id = estado.getId();
        this.nome = estado.getNome();
    }
}
