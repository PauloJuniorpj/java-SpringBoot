package com.pjestudos.pjfood.config.core.modelmapper.modelmapperConversonsUtils;

import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDto;
import com.pjestudos.pjfood.api.domain.dto.Restaurante.RestauranteDtoInput;
import com.pjestudos.pjfood.api.domain.model.Cidade;
import com.pjestudos.pjfood.api.domain.model.Cozinha;
import com.pjestudos.pjfood.api.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RestauranteModelMapperConversons {


    private ModelMapper modelMapper;

    public RestauranteModelMapperConversons(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Visualização
    public RestauranteDto toDto(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteDto.class);
    }
    //Minha entidade pro inputDto
    public RestauranteDtoInput toDtoInput(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteDtoInput.class);
    }
    //Do meu inputDto  pra entidade
    public Restaurante toDomainObject(RestauranteDtoInput restauranteDto){
        return modelMapper.map(restauranteDto, Restaurante.class);
    }

    //Visualização
    public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes){
        return restaurantes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void copyToDomainObject(RestauranteDtoInput restauranteDto, Restaurante restaurante){
        //Para evitar expetion identifier of an istance of.Cozinha as altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        if(restaurante.getEndereco() != null){
            restaurante.getEndereco().setCidade(new Cidade());
        }
        modelMapper.map(restauranteDto, restaurante);
    }
}
