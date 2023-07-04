package com.vascobank.autenticacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.vascobank.autenticacao.dto.LoginDTO;
import com.vascobank.autenticacao.dto.PageDTO;
import com.vascobank.autenticacao.http.UsuarioClient;
import com.vascobank.autenticacao.model.Usuario;

@Component
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioClient usuarioClient;

    @Override
    public UserDetails autenticar(LoginDTO loginDTO){

        Usuario usuario = getUsuarioByEmail(loginDTO.getEmail());

        if(loginDTO.getSenha().equals(usuario.getPassword())){
            return usuario;
        }

        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Senha inválida");
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        PageDTO pageDTO = usuarioClient.getUsuarioByEmail(email);
        
        if(pageDTO.getUsuario().getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email inválido");
        }

        return pageDTO.getUsuario();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PageDTO pageDTO = usuarioClient.getUsuarioByEmail(username);
        
        if(pageDTO.getUsuario().getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email inválido");
        }

        return pageDTO.getUsuario();
    }

}
