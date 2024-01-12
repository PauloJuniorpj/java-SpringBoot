package com.pjestudos.pjfood.api.domain.dto.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDto;
import com.pjestudos.pjfood.api.domain.dto.Cidade.CidadeDtoResumoDto;
import com.pjestudos.pjfood.api.domain.model.Cidade;
import com.pjestudos.pjfood.api.domain.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeDtoResumoDto cidade;


}
