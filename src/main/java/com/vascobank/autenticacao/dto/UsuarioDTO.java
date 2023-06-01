package com.vascobank.autenticacao.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.vascobank.autenticacao.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private int id;
    private String nome;
    private String email;
    // private List<PapelDTO> permissoes;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        // if(usuario.getPapeis() != null) {
        //     this.permissoes = PapelDTO.converter(usuario.getPapeis());
        // }
    }

    public static List<UsuarioDTO> converter(List<Usuario> listUsuario) {
        return listUsuario.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    public static UsuarioDTO converter(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO(usuario);
        return dto;
    }
}