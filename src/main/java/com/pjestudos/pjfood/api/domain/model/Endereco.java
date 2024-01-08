package com.pjestudos.pjfood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Endereco  {

    @Column(name = "endereco_cep")
    private String cep;
    @Column(name = "endereco_logradouro")
    private String logradouro;
    @Column(name = "endereco_numero")
    private String numero;
    @Column(name = "endereco_complemento")
    private String complemento;
    @Column(name = "endereco_bairro")
    private String bairro;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id")
    private Cidade cidade;
}
