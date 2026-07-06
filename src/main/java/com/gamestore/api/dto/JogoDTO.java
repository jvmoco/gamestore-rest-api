package com.gamestore.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record JogoDTO(
        @NotBlank(message = "O título do jogo é obrigatório")
        String titulo,

        @NotBlank(message = "O gênerp do jogo é obrigatório")
        String genero,

        @NotNull(message = "O preco do jogo é obrigatório")
        @PositiveOrZero(message = "O preço não pode ser negativo")
        BigDecimal preco,

        @NotNull(message = "O ano de lançamento do jogo é obrigatório")
        @Positive(message = "O ano de lançamento deve ser positivo")
        Integer anoLancamento,

        @NotNull(message = "As plataformas são obrigatórias")
        List<Long> plataformasIds
) {}