package com.gamestore.api.exception;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException(String mensagem){
        super(mensagem);
    }
}
