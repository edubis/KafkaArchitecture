package com.example.kafka.architecture.icompras.pedidos.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper obj = new ObjectMapper();

        obj.registerModule(new Jdk8Module());
        obj.registerModule(new JavaTimeModule());

        obj.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        obj.configure(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES,false);

        return obj;
    }
}
