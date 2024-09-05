package com.example.prodreadyfeatures.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class MapperConfig {

    @Bean
    public static ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
