package com.vascobank.autenticacao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UsuarioPapelForm {

    @NotBlank @NotNull
    private String nomePermissao;

    public UsuarioPapelForm(String nomePermissao) {
        this.setNomePermissao(nomePermissao);
    }

    public void setNomePermissao(String nomePermissao) {

        this.nomePermissao = nomePermissao;
    }
    

}
