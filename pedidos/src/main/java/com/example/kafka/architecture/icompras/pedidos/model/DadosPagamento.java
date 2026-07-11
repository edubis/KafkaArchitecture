package com.example.kafka.architecture.icompras.pedidos.model;

import com.example.kafka.architecture.icompras.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;

}
