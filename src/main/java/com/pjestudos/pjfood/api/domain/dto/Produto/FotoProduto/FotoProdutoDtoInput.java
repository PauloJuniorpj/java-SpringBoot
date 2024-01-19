package com.pjestudos.pjfood.api.domain.dto.Produto.FotoProduto;

import com.pjestudos.pjfood.config.core.validation.FileContentType;
import com.pjestudos.pjfood.config.core.validation.FileSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoProdutoDtoInput {

    @NotNull
    @FileSize(max = "500KB", message = "tamanho do arquivo invalido, maximo de 500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            message ="A foto deve ser do tipo JPG ou PNG")
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;
}
