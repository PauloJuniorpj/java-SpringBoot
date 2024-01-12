package com.pjestudos.pjfood.api.domain.dto.Cidade;

import com.pjestudos.pjfood.api.domain.dto.Estado.EstadoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDtoResumoDto {
    private Long id;
    private String nome;
    private String estado;
}
