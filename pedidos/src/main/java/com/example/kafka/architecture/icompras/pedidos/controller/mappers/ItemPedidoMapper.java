package com.example.kafka.architecture.icompras.pedidos.controller.mappers;


import com.example.kafka.architecture.icompras.pedidos.controller.dto.ItemPedidoDTO;
import com.example.kafka.architecture.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);

}
