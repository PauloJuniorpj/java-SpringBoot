package com.pjestudos.pjfood.api.domain.repository;

import com.pjestudos.pjfood.api.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
