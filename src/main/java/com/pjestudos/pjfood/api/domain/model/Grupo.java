package com.pjestudos.pjfood.api.domain.model;

import com.pjestudos.pjfood.api.domain.dto.Grupo.GrupoDtoInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nome", nullable = false)
    public String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id"),
    inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public Grupo(GrupoDtoInput grupoDtoInput) {
        nome = grupoDtoInput.getNome();
    }

    public boolean associarUmaPermissao(Permissao permissao){
        return getPermissoes().add(permissao);
    }
    public boolean desassociarPermissao (Permissao permissao){
        return getPermissoes().remove(permissao);
    }
}
