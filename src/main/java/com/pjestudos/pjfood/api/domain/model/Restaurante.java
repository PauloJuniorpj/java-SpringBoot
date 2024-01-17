package com.pjestudos.pjfood.api.domain.model;

import com.pjestudos.pjfood.config.core.validation.Groups;
import com.pjestudos.pjfood.config.core.validation.TaxaFrete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
//Aggregate Root
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @Valid
    @ManyToOne //(fetch = FetchType.LAZY)
    //tudo que termina com ToOne vai ser Eager Loading trazendo os carregamentos juntos
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.FALSE;

    @NotNull // adicionei porque o @PositiveOrZero não valida se é nulo
    @TaxaFrete
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @CreationTimestamp//atribui a data e a hora atual uma vez que a entidade foi cadastrada uma unica vezs
    @Column(name ="data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp//informa que a data e hora atual deve ser atribuida a
    // propriedade sempre que sofre uma atualização
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualização;

    @ManyToMany// nao usar os FetchType.Eager nos ManyToMany nao e performatico...
    // tudo que termina com Many e Lazy loading ou seja um carregamento por demanda
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaDePagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();


    public  void fechamento (){
        setAberto(false);
    }
    public  void abertura (){
        setAberto(true);
    }

    public  void ativar (){
        setAtivo(true);
    }
    public  void inativar (){
        setAtivo(false);
    }

    public boolean removerFormaPagamento (FormaDePagamento formaDePagamento){
        return getFormasPagamento().remove(formaDePagamento);
    }
    public boolean associarNovaFormaPagamento(FormaDePagamento formaDePagamento) {
        return getFormasPagamento().add(formaDePagamento);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return getResponsaveis().remove(usuario);
    }

    public boolean adicionarResponsavel(Usuario usuario) {
        return getResponsaveis().add(usuario);
    }

    public boolean aceitaFormaPagamento(FormaDePagamento formaPagamento) {
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaDePagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }
}
