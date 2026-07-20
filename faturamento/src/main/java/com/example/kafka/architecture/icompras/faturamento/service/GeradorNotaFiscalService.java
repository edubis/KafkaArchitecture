package com.example.kafka.architecture.icompras.faturamento.service;


import com.example.kafka.architecture.icompras.faturamento.bucket.BucketFile;
import com.example.kafka.architecture.icompras.faturamento.bucket.BucketService;
import com.example.kafka.architecture.icompras.faturamento.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;


    public void gerar(Pedido pedido) {
        log.info("Gerada nota fiscal ao Pedido{}", pedido.getCodigo());

        try {
            byte[] byteArray = notaFiscalService.gerarNota(pedido);

            String nameFile = String.format("notafiscal_pedido_%d.pdf", pedido.getCodigo());

            var file = new BucketFile(nameFile, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, Long.valueOf(byteArray.length));

            bucketService.upload(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
