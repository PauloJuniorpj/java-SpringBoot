package com.pjestudos.pjfood.api.domain.dto.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoDto {
    private Long id;
    @NotBlank
    private String descricao;
}
