package com.example.kafka.architecture.icompras.pedidos.repository;

import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
