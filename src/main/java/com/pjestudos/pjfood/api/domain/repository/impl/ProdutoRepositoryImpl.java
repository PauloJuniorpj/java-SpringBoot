package com.pjestudos.pjfood.api.domain.repository.impl;

import com.pjestudos.pjfood.api.domain.model.FotoProduto;
import com.pjestudos.pjfood.api.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Transactional
    public void delete(FotoProduto fotoProduto) {
        manager.remove(fotoProduto);
    }
}
