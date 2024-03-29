package com.pjestudos.pjfood.api.domain.dto.Cidade;

import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import com.pjestudos.pjfood.api.domain.model.Cidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDto {
    private Long id;
    private String nome;
    private EstadoDto estado;

    public CidadeDto(Cidade  cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
    }
}
