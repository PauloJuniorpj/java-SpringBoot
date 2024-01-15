package com.pjestudos.pjfood.api.domain.dto.Permissao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoDto {
    private Long id;
    private String nome;
    private String descricao;
}
