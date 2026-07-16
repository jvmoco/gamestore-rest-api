package com.gamestore.api.controller;

import com.gamestore.api.dto.DadosAutenticacao;
import com.gamestore.api.dto.DadosCadastroUsuario;
import com.gamestore.api.dto.DadosDetalhamentoUsuario;
import com.gamestore.api.dto.MudancaDeSenha;
import com.gamestore.api.exception.SenhaInvalidaException;
import com.gamestore.api.exception.UsuarioDuplicadoException;
import com.gamestore.api.model.UserRole;
import com.gamestore.api.model.Usuario;
import com.gamestore.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/registrar")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        if(repository.existsByLogin(dados.login())){
            throw new UsuarioDuplicadoException("O login informado já está em uso");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.senha());

        Usuario novoUsuario = new Usuario(dados.login(), senhaCriptografada, dados.roles());

        Usuario usuario = repository.save(novoUsuario);

        DadosDetalhamentoUsuario dadosUsuario = new DadosDetalhamentoUsuario(usuario.getId(), usuario.getLogin());

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(dadosUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestBody MudancaDeSenha mudancaDeSenha){
        if(mudancaDeSenha.senhaAtual().equals(mudancaDeSenha.senhaNova())){
            throw new SenhaInvalidaException("A nova senha não pode ser igual à senha atual.");
        }

        Usuario usuarioDoBanco = repository.findById(id).orElseThrow();

        if(!passwordEncoder.matches(mudancaDeSenha.senhaAtual(), usuarioDoBanco.getSenha())){
            throw new SenhaInvalidaException("Senha inválida");
        }

        String senhaNovaCriptografada = passwordEncoder.encode(mudancaDeSenha.senhaNova());

        usuarioDoBanco.setSenha(senhaNovaCriptografada);

        repository.save(usuarioDoBanco);

        return ResponseEntity.noContent().build();
    }
}