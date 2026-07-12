package com.example.kafka.architecture.icompras.pedidos.validator;

import com.example.kafka.architecture.icompras.pedidos.client.ClientesCLient;
import com.example.kafka.architecture.icompras.pedidos.client.ProdutosClient;
import com.example.kafka.architecture.icompras.pedidos.client.representation.ClienteRepresentation;
import com.example.kafka.architecture.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.example.kafka.architecture.icompras.pedidos.exceptions.ValidationException;
import com.example.kafka.architecture.icompras.pedidos.model.ItemPedido;
import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesCLient clientesCLient;


    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {

            var response = clientesCLient.obterDadosClientes(codigoCliente);
            ClienteRepresentation clienteRepresentation = response.getBody();
            log.info("Consulta executada com sucesso, {}, nome {}", clienteRepresentation.codigo(), clienteRepresentation.nome());
        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente de código %d não encontrado", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido item) {
        try{
            var response = produtosClient.obterDadosProduto(item.getCodigoProduto());
            ProdutoRepresentation produtoRepresentation = response.getBody();
            log.info("Produto de código {} encontrado {}", produtoRepresentation.codigo(), produtoRepresentation.nome());

        } catch (FeignException.NotFound e) {
            var message = String.format("Produto de código %d não encontrado", item.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }

}
