package com.gamestore.api.controller;

import com.gamestore.api.dto.PlataformaDTO;
import com.gamestore.api.model.Plataforma;
import com.gamestore.api.service.PlataformaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {
    private final PlataformaService plataformaService;

    public PlataformaController(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plataforma salvar(@Valid @RequestBody PlataformaDTO plataformaDTO) {
        return plataformaService.salvar(plataformaDTO);
    }

    @GetMapping
    public List<Plataforma> listarTodas() {
        return plataformaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Plataforma buscarPorId(@PathVariable Long id) {
        return plataformaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        plataformaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @Valid @RequestBody PlataformaDTO plataformaDTO){
        plataformaService.atualizar(id, plataformaDTO);
        return ResponseEntity.noContent().build();
    }
}
