package com.pjestudos.pjfood.api.domain.dto.Cidade;

import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import com.pjestudos.pjfood.api.domain.model.Cidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CidadeDto {
    private Long id;

    private EstadoDto estadoDto;

    private String nome;

    public CidadeDto(Cidade  cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
    }
}
