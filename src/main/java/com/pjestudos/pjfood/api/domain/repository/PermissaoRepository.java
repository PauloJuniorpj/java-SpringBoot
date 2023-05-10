package com.pjestudos.pjfood.api.domain.repository;


import com.pjestudos.pjfood.api.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
