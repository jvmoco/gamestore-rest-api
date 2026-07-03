package com.gamestore.api.service;

import com.gamestore.api.dto.JogoDTO;
import com.gamestore.api.exception.EntidadeNaoEncontradaException;
import com.gamestore.api.model.Jogo;
import com.gamestore.api.model.Plataforma;
import com.gamestore.api.repository.JogoRepository;
import com.gamestore.api.repository.PlataformaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogoService {
    private final JogoRepository jogoRepository;
    private final PlataformaRepository plataformaRepository;

    public JogoService(JogoRepository jogoRepository, PlataformaRepository plataformaRepository){
        this.jogoRepository = jogoRepository;
        this.plataformaRepository = plataformaRepository;
    }

    public Jogo salvar(JogoDTO jogoDTO){
        Jogo novoJogo = new Jogo();

        comporDadosDoJogo(jogoDTO, novoJogo);

        return jogoRepository.save(novoJogo);
    }

    public List<Jogo> listarTodos(){
        return jogoRepository.findAll();
    }

    public Jogo buscarPorId(Long id){
        return jogoRepository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Jogo não encontrado com id: " + id));
    }

    public void deletarPorId(Long id){
        buscarPorId(id);
        jogoRepository.deleteById(id);
    }

    public void atualizar(Long id, JogoDTO jogoDTO){
        Jogo jogoDoBanco = buscarPorId(id);

        comporDadosDoJogo(jogoDTO, jogoDoBanco);

        jogoRepository.save(jogoDoBanco);
    }

    //Métodos auxiliares
    private void comporDadosDoJogo(JogoDTO dto, Jogo destino) {
        destino.setTitulo(dto.getTitulo());
        destino.setGenero(dto.getGenero());
        destino.setPreco(dto.getPreco());
        destino.setAnoLancamento(dto.getAnoLancamento());
        List<Plataforma> plataformas = buscarEValidarPlataformas(dto.getPlataformasIds());
        destino.setPlataformas(plataformas);
    }

    private List<Plataforma> buscarEValidarPlataformas(List<Long> plataformasIds) {
        List<Long> plataformasIdsSemRepetido = plataformasIds.stream().distinct().toList();
        List<Plataforma> plataformas = plataformaRepository.findAllById(plataformasIdsSemRepetido);
        if(plataformas.size() != plataformasIdsSemRepetido.size()){
            throw new EntidadeNaoEncontradaException("Um ou mais IDs inválidos");
        }
        return plataformas;
    }
}
