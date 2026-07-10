package com.example.kafka.architecture.icompras.clientes.controller;


import com.example.kafka.architecture.icompras.clientes.model.Cliente;
import com.example.kafka.architecture.icompras.clientes.model.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        service.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("{cliente}")
    public ResponseEntity<Cliente> obterDados(@PathVariable("cliente") Long cliente) {
        return service.obterPorCodigo(cliente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

}
