package com.vascobank.autenticacao.dto;

import com.vascobank.autenticacao.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuemSouEuDTO {
    private int id;
    private String nome;
    private String email;

    public QuemSouEuDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

}