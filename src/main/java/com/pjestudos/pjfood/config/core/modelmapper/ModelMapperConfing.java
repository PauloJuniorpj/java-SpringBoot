package com.pjestudos.pjfood.config.core.modelmapper;

import com.pjestudos.pjfood.api.domain.dto.Endereco.EnderecoDto;
import com.pjestudos.pjfood.api.domain.dto.ItemPedido.ItemPedidoInputDto;
import com.pjestudos.pjfood.api.domain.model.Endereco;
import com.pjestudos.pjfood.api.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfing {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        // Configurar o TypeMap durante a criação do ModelMapper
        TypeMap<Endereco, EnderecoDto> enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDto.class
        );
        enderecoToEnderecoModelTypeMap.<String>addMapping(
                org -> org.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value)
        );

        modelMapper.createTypeMap(ItemPedidoInputDto.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
