package com.pjestudos.pjfood.api.domain.repository;

import com.pjestudos.pjfood.api.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
    FotoProduto save(FotoProduto foto);
    void delete (FotoProduto fotoProduto);
}
