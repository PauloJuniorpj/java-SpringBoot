package com.pjestudos.pjfood.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
//Aggregate Root
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    private String nome;

    @JsonIgnore
    @NotNull
    @Valid
    @ManyToOne //(fetch = FetchType.LAZY)
    //tudo que termina com ToOne vai ser Eager Loading trazendo os carregamentos juntos
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @PositiveOrZero
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @JsonIgnore
    @CreationTimestamp//atribui a data e a hora atual uma vez que a entidade foi cadastrada uma unica vezs
    @Column(name ="data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp//informa que a data e hora atual deve ser atribuida a
    // propriedade sempre que sofre uma atualização
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualização;

    @JsonIgnore
    @ManyToMany// nao usar os FetchType.Eager nos ManyToMany nao e performatico...
    // tudo que termina com Many e Lazy loading ou seja um carregamento por demanda
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaDePagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
}
