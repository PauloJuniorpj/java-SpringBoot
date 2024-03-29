package com.pjestudos.pjfood.api.domain.repository;

import com.pjestudos.pjfood.api.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

}
