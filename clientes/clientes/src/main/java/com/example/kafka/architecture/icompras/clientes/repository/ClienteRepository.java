package com.example.kafka.architecture.icompras.clientes.repository;

import com.example.kafka.architecture.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
