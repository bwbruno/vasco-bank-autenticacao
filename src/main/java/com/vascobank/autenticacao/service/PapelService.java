package com.vascobank.autenticacao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vascobank.autenticacao.model.Papel;

@Service
public interface PapelService {
    public Papel savePapel(Papel papel);

    public void removePapel(Long id);

    Papel getPapelByNome(String nome); 

    public Papel getPapelById(Long id);

    public List<Papel> getListPapel();

    public void atualizarPapel(Long id, String nome);
}
