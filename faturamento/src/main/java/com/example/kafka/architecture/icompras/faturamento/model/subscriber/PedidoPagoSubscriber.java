package com.example.kafka.architecture.icompras.faturamento.model.subscriber;


import com.example.kafka.architecture.icompras.faturamento.mapper.PedidoMapper;
import com.example.kafka.architecture.icompras.faturamento.model.Pedido;
import com.example.kafka.architecture.icompras.faturamento.model.subscriber.representation.DetalheItemPedidoRepresentationDTO;
import com.example.kafka.architecture.icompras.faturamento.model.subscriber.representation.DetalhePedidoRepresentationDTO;
import com.example.kafka.architecture.icompras.faturamento.service.GeradorNotaFiscalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService geradorNotaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void listen(String json) {
        try {

            log.info("Recebendo pedido para faturamento: {}", json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentationDTO.class);
            Pedido pedido = pedidoMapper.map(representation);

            geradorNotaFiscalService.gerar(pedido);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
