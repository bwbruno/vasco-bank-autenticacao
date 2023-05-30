package com.vascobank.autenticacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vascobank.autenticacao.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

    Optional<Papel> findByNome(String nome);

}
