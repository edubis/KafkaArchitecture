package com.example.kafka.architecture.icompras.pedidos.service;

import com.example.kafka.architecture.icompras.pedidos.client.ServicoBancarioClient;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.example.kafka.architecture.icompras.pedidos.repository.ItemPedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.repository.PedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioCliente;


    @Transactional
    public Pedido salvarPedido(Pedido pedido){
        validator.validar(pedido);
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        pedido.setChavePagamento(servicoBancarioCliente.solicitarPagamento(pedido));
        return pedido;
    }

    public Optional<Pedido> obterPedido(Long pedido){
        return repository.findById(pedido);
    }

}
