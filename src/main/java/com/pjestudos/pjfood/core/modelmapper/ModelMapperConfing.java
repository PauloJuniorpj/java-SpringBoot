package com.pjestudos.pjfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfing {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
