package com.example.kafka.architecture.icompras.produtos.service;

import com.example.kafka.architecture.icompras.produtos.model.Produto;
import com.example.kafka.architecture.icompras.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> obterPorCodigo (Long codigo){
        return repository.findById(codigo);
    }

}
