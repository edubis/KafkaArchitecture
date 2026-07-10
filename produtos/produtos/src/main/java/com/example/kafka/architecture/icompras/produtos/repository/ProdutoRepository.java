package com.example.kafka.architecture.icompras.produtos.repository;

import com.example.kafka.architecture.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository  extends JpaRepository<Produto, Long> {
}
