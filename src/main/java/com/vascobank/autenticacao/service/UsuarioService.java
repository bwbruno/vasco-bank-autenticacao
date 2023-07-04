package com.vascobank.autenticacao.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.vascobank.autenticacao.dto.LoginDTO;
import com.vascobank.autenticacao.model.Usuario;


@Service
public interface UsuarioService extends UserDetailsService {
    public UserDetails autenticar(LoginDTO loginDTO);
    public Usuario getUsuarioByEmail(String email);
}
