package com.gamestore.api.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErroDeValidacao extends ErroPadrao{
    private List<FieldMessage> erros = new ArrayList<>();

    public ErroDeValidacao(Instant timestamp, Integer status, String name, String message, String path, List<FieldMessage> erros) {
        super(timestamp, status, name, message, path);
        this.erros = erros;
    }

    public List<FieldMessage> getErros() {
        return erros;
    }

    public void setErros(List<FieldMessage> erros) {
        this.erros = erros;
    }

    public void adicionarErro(FieldMessage erro){
        erros.add(erro);
    }
}
