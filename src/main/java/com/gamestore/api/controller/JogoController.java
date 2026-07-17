package com.gamestore.api.controller;

import com.gamestore.api.dto.JogoDTO;
import com.gamestore.api.model.Jogo;
import com.gamestore.api.service.JogoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {
    private final JogoService jogoService;

    public JogoController(JogoService jogoService){
        this.jogoService = jogoService;
    }

    @PostMapping
    public ResponseEntity<Jogo> salvar(@Valid @RequestBody JogoDTO jogoDTO, UriComponentsBuilder uriBuilder){
        Jogo jogo =  jogoService.salvar(jogoDTO);

        var uri = uriBuilder.path("/jogos/{id}").buildAndExpand(jogo.getId()).toUri();

        return ResponseEntity.created(uri).body(jogo);
    }

    @GetMapping
    public ResponseEntity<List<Jogo>> listarTodos(){
        List<Jogo> jogos = jogoService.listarTodos();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarPorId(@PathVariable Long id){
        Jogo jogo = jogoService.buscarPorId(id);
        return ResponseEntity.ok(jogo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        jogoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id,@Valid @RequestBody JogoDTO jogoDTO){
        jogoService.atualizar(id, jogoDTO);
        return ResponseEntity.noContent().build();
    }
}
