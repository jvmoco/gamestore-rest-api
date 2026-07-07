package com.gamestore.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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
        return montarRespostaErro(HttpStatus.NOT_FOUND, ex.getMessage(), request);
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

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErroPadrao> trataBadCredentials(org.springframework.security.authentication.BadCredentialsException ex, HttpServletRequest request) {
        return montarRespostaErro(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> redeDeSegurancaGlobal(Exception ex, HttpServletRequest request){
        String msg = "Ocorreu um erro interno inesperado no servidor. Por favor, tente novamente mais tarde.";
        return montarRespostaErro(HttpStatus.INTERNAL_SERVER_ERROR, msg, request);
    }

    @ExceptionHandler(UsuarioDuplicadoException.class)
    public ResponseEntity<ErroPadrao> trataUsuarioDuplicado(UsuarioDuplicadoException ex, HttpServletRequest request){
        return montarRespostaErro(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<ErroPadrao> trataSenhaInvalida(SenhaInvalidaException ex, HttpServletRequest request){
        return montarRespostaErro(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErroPadrao> trataValidacaoException(ValidacaoException ex, HttpServletRequest request){
        return montarRespostaErro(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    private ResponseEntity<ErroPadrao> montarRespostaErro(HttpStatus status, String mensagem, HttpServletRequest request){
        ErroPadrao erroPadrao = new ErroPadrao(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(erroPadrao);
    }
}
