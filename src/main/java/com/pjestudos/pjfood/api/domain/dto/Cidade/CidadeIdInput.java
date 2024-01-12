package com.pjestudos.pjfood.api.domain.dto.Cidade;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeIdInput {
    @NotNull
    Long id;
}
