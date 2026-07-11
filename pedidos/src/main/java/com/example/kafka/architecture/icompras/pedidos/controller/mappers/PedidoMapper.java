package com.example.kafka.architecture.icompras.pedidos.controller.mappers;


import com.example.kafka.architecture.icompras.pedidos.controller.dto.ItemPedidoDTO;
import com.example.kafka.architecture.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.example.kafka.architecture.icompras.pedidos.model.ItemPedido;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.example.kafka.architecture.icompras.pedidos.model.enums.Status;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source = "dadosPagamento", target = "dadosPagamento")
    Pedido map(NovoPedidoDTO pedido);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> dtos) {
        return dtos.stream().map(ITEM_PEDIDO_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(Status.REALIOZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = getAbs(pedido);

        pedido.setTotal(total);
        pedido.getItens().forEach( itemPedido -> itemPedido.setPedido(pedido));


    }

    private static BigDecimal getAbs(Pedido pedido) {
        return pedido.getItens().stream().map(item -> {
            return item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }

}
