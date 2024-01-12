package com.pjestudos.pjfood.api.domain.dto.Grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDto {
    public Long id;
    public String nome;
}
