package com.vascobank.autenticacao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vascobank.autenticacao.exception.RegraNegocioException;
import com.vascobank.autenticacao.model.Papel;
import com.vascobank.autenticacao.repository.PapelRepository;

@Component
public class PapelServiceImpl implements PapelService {

    @Autowired
    PapelRepository papelRepository;

    @Override
    public Papel savePapel(Papel papel) {
        return papelRepository.save(papel);
    }

    @Override
    public void removePapel(Long id) {
       papelRepository.deleteById(id);
    }

    @Override
    public Papel getPapelById(Long id) {
        return papelRepository
                        .findById(id)
                        .map(
                            papel -> {
                                return papel;
                            }
                        ).orElseThrow(
                            () -> new RegraNegocioException("Permissão '" + id + "' não foi encontrada")
                        );
    }

    @Override
    public Papel getPapelByNome(String nome) {
        return papelRepository
                        .findByNome(nome)
                        .map(
                            papel -> {
                                return papel;
                            }
                        ).orElseThrow(
                            () -> new RegraNegocioException("Permissão '" + nome + "' não foi encontrada")
                        );
    }

    @Override
    public List<Papel> getListPapel() {
        return papelRepository.findAll();
    }

    @Override
    public void atualizarPapel(Long id, String nome) {
      var p = papelRepository.getReferenceById(id);
      p.setNome(nome);
      papelRepository.save(p);
    }
   
}