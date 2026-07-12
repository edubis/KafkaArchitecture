package com.example.kafka.architecture.icompras.pedidos.client;

import com.example.kafka.architecture.icompras.pedidos.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes",url="${icompras.pedidos.clients.clientes.url}")
public interface ClientesCLient {

    @GetMapping("{codigo}")
    ResponseEntity<ClienteRepresentation> obterDadosClientes(@PathVariable("codigo") Long codigo);

}
