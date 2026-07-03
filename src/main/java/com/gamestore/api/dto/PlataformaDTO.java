package com.gamestore.api.dto;

import jakarta.validation.constraints.NotBlank;

public class PlataformaDTO {
    @NotBlank(message = "O nome da plataforma é obrigatório")
    private String nome;

    public PlataformaDTO(){}

    public PlataformaDTO(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
