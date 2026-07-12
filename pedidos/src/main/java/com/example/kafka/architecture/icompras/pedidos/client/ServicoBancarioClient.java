package com.example.kafka.architecture.icompras.pedidos.client;


import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ServicoBancarioClient {


    public String solicitarPagamento(Pedido pedido){
        log.info("Solicitando Pagamento para o pedido {}",pedido.getCodigo());
        return UUID.randomUUID().toString();
    }

}
