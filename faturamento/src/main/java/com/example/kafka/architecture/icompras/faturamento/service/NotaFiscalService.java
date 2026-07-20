package com.example.kafka.architecture.icompras.faturamento.service;


import com.example.kafka.architecture.icompras.faturamento.model.Pedido;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotaFiscalService {

    @Value("classpath:/templates/nota-fiscal.jrxml")
    private Resource notaFiscal;


    public byte [] gerarNota(Pedido pedido){
    try(InputStream is = notaFiscal.getInputStream()){

        Map<String, Object> params = new HashMap<>();
        params.put("NOME", pedido.getCliente().getNome());
        params.put("CPF", pedido.getCliente().getCpf());
        params.put("LOGRADOURO", pedido.getCliente().getLogradouro());
        params.put("NUMERO", pedido.getCliente().getNumero());
        params.put("EMAIL", pedido.getCliente().getEmail());
        params.put("TELEFONE", pedido.getCliente().getEmail());
        params.put("BAIRRO", pedido.getCliente().getBairro());

        var dataSource = new JRBeanCollectionDataSource(pedido.getItens());

        params.put("DATA_PEDIDO", pedido.getData());
        params.put("TOTAL_PEDIDO", pedido.getTotal());

        JasperReport jasperReport = JasperCompileManager.compileReport(is);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    }

}
