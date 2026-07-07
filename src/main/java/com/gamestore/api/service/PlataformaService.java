package com.gamestore.api.service;

import com.gamestore.api.dto.PlataformaDTO;
import com.gamestore.api.exception.EntidadeNaoEncontradaException;
import com.gamestore.api.exception.ValidacaoException;
import com.gamestore.api.model.Plataforma;
import com.gamestore.api.repository.JogoRepository;
import com.gamestore.api.repository.PlataformaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlataformaService {
    private final PlataformaRepository plataformaRepository;
    private final JogoRepository jogoRepository;

    public PlataformaService(PlataformaRepository plataformaRepository, JogoRepository jogoRepository){
        this.plataformaRepository = plataformaRepository;
        this.jogoRepository = jogoRepository;
    }

    public Plataforma salvar(PlataformaDTO plataformaDTO){
        Plataforma plataforma = new Plataforma();
        plataforma.setNome(plataformaDTO.nome());
        return plataformaRepository.save(plataforma);
    }

    public List<Plataforma> listarTodas(){
        return plataformaRepository.findAll();
    }

    public Plataforma buscarPorId(Long id){
        return plataformaRepository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Plataforma não encontrada com id: " + id));
    }

    public void deletarPorId(Long id){
        buscarPorId(id);
        if(jogoRepository.existsByPlataformasId(id)){
            throw new ValidacaoException("Essa plataforma não pode ser deletada pois está em uso");
        }
        plataformaRepository.deleteById(id);
    }

    public void atualizar(Long id, PlataformaDTO plataformaDTO){
        Plataforma plataformaDoBanco = buscarPorId(id);
        plataformaDoBanco.setNome(plataformaDTO.nome());
        plataformaRepository.save(plataformaDoBanco);
    }
}
