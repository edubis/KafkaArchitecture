package com.example.kafka.architecture.icompras.pedidos.controller.dto;

import com.example.kafka.architecture.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(
        String dados,
        TipoPagamento tipoPagamento
) {
}
