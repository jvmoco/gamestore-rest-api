package com.gamestore.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JogoDTO {
    @NotBlank(message = "o título do jogo é obrigatório")
    private String titulo;
    @NotBlank(message = "o gênero do jogo é obrigatório")
    private String genero;
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;
    @NotNull
    @Positive
    private Integer anoLancamento;
    @NotNull
    private List<Long> plataformasIds = new ArrayList<>();

    public JogoDTO(){}

    public JogoDTO(String titulo, String genero, BigDecimal preco, Integer anoLancamento, List<Long> plataformasIds){
        this.titulo = titulo;
        this.genero = genero;
        this.preco = preco;
        this.anoLancamento = anoLancamento;
        this.plataformasIds = plataformasIds;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public @NotNull List<Long> getPlataformasIds() {
        return plataformasIds;
    }

    public void setPlataformasIds(@NotNull List<Long> plataformasIds) {
        this.plataformasIds = plataformasIds;
    }
}