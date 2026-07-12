package com.example.kafka.architecture.icompras.pedidos.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Component
public class DecoderBase64 {

    public String myDecode64(String encode) {
        byte[] decodedApiKey = Base64.getDecoder().decode(encode);
        return new String(decodedApiKey, StandardCharsets.UTF_8);
    }

}
