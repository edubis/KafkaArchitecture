package com.example.kafka.architecture.icompras.pedidos.model;

import com.example.kafka.architecture.icompras.pedidos.client.representation.ClienteRepresentation;
import com.example.kafka.architecture.icompras.pedidos.controller.dto.DadosPagamentoDTO;
import com.example.kafka.architecture.icompras.pedidos.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="codigo")
    private Long codigo;

    @Column(name = "codigo_cliente")
    private Long codigoCliente;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Column(name = "chave_pagamento")
    private String chavePagamento;

    @Column(name="observacoes")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @Column(name ="total",nullable = false, precision = 16, scale = 2)
    private BigDecimal total;

    @Column(name="codigo_rastreio", length = 255)
    private String codigoRastreio;

    @Column(name="url_nf")
    private String urlNf;

    @OneToMany(

            mappedBy = "pedido"

    )
    private List<ItemPedido> itens;

    @Transient
    private ClienteRepresentation dadosCliente;

    @Transient
    private DadosPagamento dadosPagamento;

}

