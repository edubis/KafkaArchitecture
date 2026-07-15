package com.example.kafka.architecture.icompras.pedidos.model.enums.exception;

public class ItemNaoEncontradoException extends RuntimeException{
    public ItemNaoEncontradoException(String message) {
        super(message);
    }
}
