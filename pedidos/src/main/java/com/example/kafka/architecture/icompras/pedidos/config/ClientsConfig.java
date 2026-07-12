package com.example.kafka.architecture.icompras.pedidos.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.kafka.architecture.icompras.pedidos.client" )
public class ClientsConfig {

}
