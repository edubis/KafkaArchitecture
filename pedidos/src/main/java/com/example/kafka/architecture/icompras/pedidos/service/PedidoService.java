package com.example.kafka.architecture.icompras.pedidos.service;

import com.example.kafka.architecture.icompras.pedidos.client.ServicoBancarioClient;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.example.kafka.architecture.icompras.pedidos.model.enums.Status;
import com.example.kafka.architecture.icompras.pedidos.repository.ItemPedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.repository.PedidoRepository;
import com.example.kafka.architecture.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioCliente;


    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        validator.validar(pedido);
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        pedido.setChavePagamento(servicoBancarioCliente.solicitarPagamento(pedido));
        return pedido;
    }

    public Optional<Pedido> obterPedido(Long pedido) {
        return repository.findById(pedido);
    }

    public void atualizarStatusPagamento(
            Long codigoPedido, String chavePagamento,
            boolean sucesso, String observacoes
    ) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if (pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido não encontrado para o código %d e a chave de pagamento %s", codigoPedido, chavePagamento);

            log.error(msg);

            return;
        }

        Pedido pedido = pedidoEncontrado.get();

        if(sucesso){
            pedido.setStatus(Status.PAGO);
            pedido.setObservacoes(observacoes);
        }else{
            pedido.setStatus(Status.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        repository.save(pedido);

    }

}
