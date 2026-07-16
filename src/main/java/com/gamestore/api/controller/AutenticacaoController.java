package com.gamestore.api.controller;

import com.gamestore.api.dto.DadosAutenticacao;
import com.gamestore.api.dto.DadosTokenJWT;
import com.gamestore.api.model.Usuario;
import com.gamestore.api.service.TokenService;
import jakarta.validation.Valid;
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

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.manager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var authentication = manager.authenticate(authenticationToken);

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        String tokenJWT = tokenService.gerarToken(usuarioLogado);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}