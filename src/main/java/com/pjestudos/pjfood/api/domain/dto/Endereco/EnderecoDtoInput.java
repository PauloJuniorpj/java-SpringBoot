package com.pjestudos.pjfood.api.domain.dto.Endereco;

import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDto;
import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDtoResumoDto;
import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeIdInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDtoInput {
    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    private String complemento;
    @NotBlank
    private String bairro;

    @NotBlank
    @Valid
    private CidadeIdInput cidade;
}
