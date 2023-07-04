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
    private String senha;
    // private List<PapelDTO> permissoes;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        // if(usuario.getPapeis() != null) {
        //     this.permissoes = PapelDTO.converter(usuario.getPapeis());
        // }
    }

    public static List<UsuarioDTO> converter(List<Usuario> listUsuario) {
        return listUsuario.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    public static Usuario converterParaUsuario(UsuarioDTO usuario) {
        return Usuario.builder()
                            .id(usuario.getId())
                            .nome(usuario.getNome())
                            .email(usuario.getEmail())
                            .senha(usuario.getSenha())
                            .build();
    }
}