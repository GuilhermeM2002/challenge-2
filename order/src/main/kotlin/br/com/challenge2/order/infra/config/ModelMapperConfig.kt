package br.com.challenge2.order.infra.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper () : ModelMapper{
        return ModelMapper()
    }
}