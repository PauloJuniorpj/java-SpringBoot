package com.pjestudos.pjfood.api.domain.dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioComSenhaInput extends UsuarioInput{
    private String senha;
}
