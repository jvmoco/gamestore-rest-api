package com.gamestore.api.controller;

import com.gamestore.api.dto.DadosAutenticacao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager; // A tomada mágica puxando o gerenciador que expusemos na configuração

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // 1. Transforma o login e senha que vieram do Postman em um "Token" de autenticação do Spring
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // 2. Entrega para o Gerenciador validar. Ele vai chamar o seu Service, que chama o seu Repositório, que vai ao Banco.
        var authentication = manager.authenticate(authenticationToken);

        // 3. Se a senha estiver certa, por enquanto vamos apenas devolver um OK (200)
        return ResponseEntity.ok().build();
    }
}