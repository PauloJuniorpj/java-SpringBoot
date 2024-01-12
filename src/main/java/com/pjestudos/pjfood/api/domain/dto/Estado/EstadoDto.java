package com.pjestudos.pjfood.api.domain.dto.Estado;

import com.pjestudos.pjfood.api.domain.model.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDto {
    private  Long id;
    private String nome;



    public EstadoDto(Estado estado) {
        this.id = estado.getId();
        this.nome = estado.getNome();
    }
}
