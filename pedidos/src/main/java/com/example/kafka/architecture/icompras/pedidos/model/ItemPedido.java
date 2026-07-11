package com.example.kafka.architecture.icompras.pedidos.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Data
public class ItemPedido {

    /*
    codigo serial not null primary key,
	codigo_pedido bigint not null references pedido (codigo),
	codigo_produto bigint not null,
	quantidade int not null,
	valor_unitario decimal (16,2) not null*/


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "codigo_produto", nullable = false)
    private Long codigoProduto;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "valor_unitario", precision = 16, scale = 2)
    private BigDecimal valorUnitario;

}
