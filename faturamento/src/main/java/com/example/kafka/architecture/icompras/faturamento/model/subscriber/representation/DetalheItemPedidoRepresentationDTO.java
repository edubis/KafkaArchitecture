package com.example.kafka.architecture.icompras.faturamento.model.subscriber.representation;

import java.math.BigDecimal;


public record DetalheItemPedidoRepresentationDTO(
        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal total
) {


}
