package com.example.kafka.architecture.icompras.pedidos.repository;

import com.example.kafka.architecture.icompras.pedidos.model.ItemPedido;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
