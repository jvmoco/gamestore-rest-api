package com.gamestore.api.dto;

import jakarta.validation.constraints.NotBlank;

public record PlataformaDTO(
        @NotBlank(message = "O nome da plataforma é obrigatório")
        String nome
){}
