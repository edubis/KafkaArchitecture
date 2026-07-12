package com.example.kafka.architecture.icompras.pedidos.model;

public record ResponseCallBackPagamentoDTO(
        Long codigo,
        String chavePagamento,
        boolean status,
        String observacoes
) {
}
