package com.example.kafka.architecture.icompras.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ItemPedido{

    private Long codigo;
    private String nome;
    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal total;

}
