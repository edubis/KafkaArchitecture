package com.example.kafka.architecture.icompras.pedidos.publisher.representation;

import com.example.kafka.architecture.icompras.pedidos.model.enums.Status;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoRepresentationDTO(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        Status status,
        List<DetalheItemPedidoRepresentationDTO> itens
) {
}
