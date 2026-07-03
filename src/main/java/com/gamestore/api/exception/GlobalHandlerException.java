package com.gamestore.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalHandlerException{
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroPadrao> trataEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, HttpServletRequest request){
        String urlAcessada = request.getRequestURI();

        ErroPadrao erroPadrao = new ErroPadrao(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                urlAcessada
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDeValidacao> trataErroDeValidacao(MethodArgumentNotValidException ex, HttpServletRequest request){
        String urlAcessada = request.getRequestURI();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldMessage> fieldMessages = new ArrayList<>();
        for(FieldError fieldError : fieldErrors){
            String campo = fieldError.getField();
            String mensagem = fieldError.getDefaultMessage();

            FieldMessage fieldMessage = new FieldMessage(campo, mensagem);

            fieldMessages.add(fieldMessage);
        }

        ErroDeValidacao erroDeValidacao = new ErroDeValidacao(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                urlAcessada,
                fieldMessages
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDeValidacao);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> redeDeSegurancaGlobal(Exception ex, HttpServletRequest request){
        String urlAcessada = request.getRequestURI();

        ErroPadrao erroPadrao = new ErroPadrao(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocorreu um erro interno inesperado no servidor. Por favor, tente novamente mais tarde.",
                urlAcessada
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroPadrao);
    }
}
