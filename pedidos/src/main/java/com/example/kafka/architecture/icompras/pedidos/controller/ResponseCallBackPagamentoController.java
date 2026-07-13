package com.example.kafka.architecture.icompras.pedidos.controller;


import com.example.kafka.architecture.icompras.pedidos.exceptions.ValidationException;
import com.example.kafka.architecture.icompras.pedidos.model.ApiKey;
import com.example.kafka.architecture.icompras.pedidos.model.ErroResposta;
import com.example.kafka.architecture.icompras.pedidos.model.ResponseCallBackPagamentoDTO;
import com.example.kafka.architecture.icompras.pedidos.service.PedidoService;
import com.example.kafka.architecture.icompras.pedidos.utils.DecoderBase64;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class ResponseCallBackPagamentoController {

    private final PedidoService pedidoService;

    private final ApiKey appKey;

    private final DecoderBase64 myDecode;

    @PostMapping
    public ResponseEntity<Object> atualizaStatusPagamento(
            @RequestBody ResponseCallBackPagamentoDTO body,
            @RequestHeader (required = true, name = "apiKey") String apiKey
            ){

        var decodedValue = myDecode.myDecode64(apiKey);

        if(!appKey.getApi().equals(decodedValue)){

           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não foi possível realizar autenticação!");
        }

        pedidoService.atualizarStatusPagamento(
                body.codigo(),
                body.chavePagamento(),
                body.status(),
                body.observacoes()
        );

        return ResponseEntity.ok().build();

    }


}
