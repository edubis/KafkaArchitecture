package com.example.kafka.architecture.icompras.pedidos.controller.dto;

import com.example.kafka.architecture.icompras.pedidos.model.enums.TipoPagamento;

public record NovoPagamentoDTO(

        Long codigoPedido,
        String dados,
        TipoPagamento tipoPagamento
) {
}
