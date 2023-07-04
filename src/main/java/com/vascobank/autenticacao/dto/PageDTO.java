package com.vascobank.autenticacao.dto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.vascobank.autenticacao.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {

    List<UsuarioDTO> content;

    public Usuario getUsuario() {
        if(content.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email inválido");
        }
        UsuarioDTO usuarioDTO = this.content.get(0);
        return UsuarioDTO.converterParaUsuario(usuarioDTO);
    }
}
