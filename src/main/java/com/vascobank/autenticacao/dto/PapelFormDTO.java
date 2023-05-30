package com.vascobank.autenticacao.dto;

import com.vascobank.autenticacao.model.Papel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PapelFormDTO {

    @NotBlank @NotNull
    private String nome;

    public Papel converter() {
        Papel permissao = new Papel(this.nome);
        return permissao;
    }

}
