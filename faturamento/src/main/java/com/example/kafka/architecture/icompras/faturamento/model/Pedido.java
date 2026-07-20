package com.example.kafka.architecture.icompras.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Pedido {
    private Long codigo;
    private Cliente cliente;
    private String data;
    private BigDecimal total;
    private List<ItemPedido> itens;
}
