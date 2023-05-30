package com.vascobank.autenticacao.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.vascobank.autenticacao.model.Papel;
import com.vascobank.autenticacao.model.Usuario;


@Service
public interface UsuarioService extends UserDetailsService {
    
    public Usuario salvar(Usuario usuario);

    public void removeUsuario(Usuario usuario);

    public Usuario getUsuarioById(Integer id);

    public Usuario findByEmail(String email);

    public List<Usuario> getListUsuario();

    public Usuario atribuirPapel(Integer id, Papel papel);    

    public Usuario atribuirPapelPorEmail(String email, Papel papel); 

    public UserDetails autenticar(Usuario usuario);

    public Boolean isEmailNotUsed(Usuario usuario);

}
