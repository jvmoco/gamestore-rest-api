package com.gamestore.api.dto;

import com.gamestore.api.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record DadosCadastroUsuario(
        @NotBlank(message = "O login é obrigatório.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @NotEmpty(message = "O usuário deve ter pelo menos um perfil de acesso (Role).")
        Set<UserRole> roles
) {}