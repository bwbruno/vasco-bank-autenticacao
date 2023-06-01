package com.vascobank.autenticacao.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.vascobank.autenticacao.dto.UsuarioDTO;
import com.vascobank.autenticacao.model.Usuario;
import com.vascobank.autenticacao.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    @SecurityRequirement(name = "bearer-key")
    public List<UsuarioDTO> find() {
       return UsuarioDTO.converter(usuarioService.getListUsuario()); 
    }

    @GetMapping("/usuario/{id}")
    @SecurityRequirement(name = "bearer-key")
    public UsuarioDTO findById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return UsuarioDTO.converter(usuario);
    }

}
