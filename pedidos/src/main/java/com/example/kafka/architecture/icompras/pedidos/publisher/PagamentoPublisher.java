package com.example.kafka.architecture.icompras.pedidos.publisher;


import com.example.kafka.architecture.icompras.pedidos.model.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoPublisher {

    private final DetalhePedidoMapper detalheMapper;
    private final ObjectMapper objMapper;
    private final KafkaTemplate<String, String> kfkTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar (Pedido pedido){
        log.info("Publicando pedido pago, {}", pedido.getCodigo());

        try{
            var representation = detalheMapper.map(pedido);
            var json = objMapper.writeValueAsString(representation);
            kfkTemplate.send(topico, "dados", json);

        }catch (JsonProcessingException e) {
            log.error("Erro ao processar Json", e);
        }catch(RuntimeException e){
            log.error("Erro tecnico ao realizar processo", e);
        }
    }

}
