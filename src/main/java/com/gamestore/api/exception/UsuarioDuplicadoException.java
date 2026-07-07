package com.gamestore.api.exception;

public class UsuarioDuplicadoException extends RuntimeException{
    public UsuarioDuplicadoException(String mensagem){
        super(mensagem);
    }
}
