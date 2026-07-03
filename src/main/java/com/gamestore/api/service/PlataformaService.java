package com.gamestore.api.service;

import com.gamestore.api.dto.PlataformaDTO;
import com.gamestore.api.exception.EntidadeNaoEncontradaException;
import com.gamestore.api.model.Plataforma;
import com.gamestore.api.repository.PlataformaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlataformaService {
    private final PlataformaRepository plataformaRepository;

    public PlataformaService(PlataformaRepository plataformaRepository){
        this.plataformaRepository = plataformaRepository;
    }

    public Plataforma salvar(PlataformaDTO plataformaDTO){
        Plataforma plataforma = new Plataforma();
        plataforma.setNome(plataformaDTO.getNome());
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
        plataformaRepository.deleteById(id);
    }

    public void atualizar(Long id, PlataformaDTO plataformaDTO){
        Plataforma plataformaDoBanco = buscarPorId(id);
        plataformaDoBanco.setNome(plataformaDTO.getNome());
        plataformaRepository.save(plataformaDoBanco);
    }
}
