package com.gamestore.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gamestore.api.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TokenService {
    private String secret = "minha-chave-secreta-123";
    private String issuer = "GameStore-API";

    public String gerarToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            List<String> asRoles = usuario.getRoles().stream().map(role -> "ROLE_" + role.name()).toList();
            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(usuario.getLogin())
                    .withClaim("roles", asRoles)
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException ex){
            throw new RuntimeException("Erro ao gerar token JWT");
        }
    }

    public String validarToken(String tokenJWT){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            var validador = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            DecodedJWT tokenDecodificado = validador.verify(tokenJWT);

            String loginDoUsuario = tokenDecodificado.getSubject();
            return loginDoUsuario;
        }catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant dataExpiracao() {
        return java.time.LocalDateTime.now()
                .plusHours(2)
                .toInstant(java.time.ZoneOffset.of("-03:00"));
    }
}
