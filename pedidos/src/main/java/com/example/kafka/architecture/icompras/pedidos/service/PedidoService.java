package com.example.kafka.architecture.icompras.pedidos.service;

import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.example.kafka.architecture.icompras.pedidos.repository.ItemPedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;

    public Pedido salvarPedido(Pedido pedido){

        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }

    public Optional<Pedido> obterPedido(Long pedido){
        return repository.findById(pedido);
    }

}
