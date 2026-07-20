package com.example.kafka.architecture.icompras.faturamento.mapper;

import com.example.kafka.architecture.icompras.faturamento.model.Cliente;
import com.example.kafka.architecture.icompras.faturamento.model.ItemPedido;
import com.example.kafka.architecture.icompras.faturamento.model.Pedido;
import com.example.kafka.architecture.icompras.faturamento.model.subscriber.representation.DetalheItemPedidoRepresentationDTO;
import com.example.kafka.architecture.icompras.faturamento.model.subscriber.representation.DetalhePedidoRepresentationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentationDTO representationDTO) {
        Cliente cliente = new Cliente(
                representationDTO.nome(), representationDTO.cpf(), representationDTO.logradouro(), representationDTO.numero(), representationDTO.bairro(), representationDTO.email(), representationDTO.telefone()
        );

        List<ItemPedido> itens = representationDTO.itens().stream().map(this::mapItem).toList();

        return new Pedido(representationDTO.codigo(), cliente, representationDTO.dataPedido(), representationDTO.total(), itens);

    }


    private ItemPedido mapItem(DetalheItemPedidoRepresentationDTO representationDTO){
        return new ItemPedido(representationDTO.codigoProduto(), representationDTO.nome(), representationDTO.valorUnitario(), representationDTO.quantidade(),representationDTO.total());
    }

}
