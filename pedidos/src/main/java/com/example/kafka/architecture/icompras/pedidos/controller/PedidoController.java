package com.example.kafka.architecture.icompras.pedidos.controller;


import com.example.kafka.architecture.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.example.kafka.architecture.icompras.pedidos.controller.mappers.PedidoMapper;
import com.example.kafka.architecture.icompras.pedidos.exceptions.ValidationException;
import com.example.kafka.architecture.icompras.pedidos.model.ErroResposta;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.example.kafka.architecture.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("pedidos")
@RestController
@RequiredArgsConstructor
public class PedidoController {


    private final PedidoService service;
    private final PedidoMapper mapper;


    @PostMapping
    public ResponseEntity<Object> registrarPedido(@RequestBody NovoPedidoDTO dto){

            var pedido = mapper.map(dto);
            var novoPedido = service.salvarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());

    }

    @GetMapping("{pedido}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable("pedido") Long pedido){
        return service.obterPedido(pedido)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity
                        .notFound()
                        .build());
    }


}
