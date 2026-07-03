package com.gamestore.api.controller;

import com.gamestore.api.dto.JogoDTO;
import com.gamestore.api.model.Jogo;
import com.gamestore.api.service.JogoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {
    private final JogoService jogoService;

    public JogoController(JogoService jogoService){
        this.jogoService = jogoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jogo salvar(@Valid @RequestBody JogoDTO jogoDTO){
        return jogoService.salvar(jogoDTO);
    }

    @GetMapping
    public List<Jogo> listarTodos(){
        return jogoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Jogo buscarPorId(@PathVariable Long id){
        return jogoService.buscarPorId(id);
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
