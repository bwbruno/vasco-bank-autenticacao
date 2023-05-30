package com.vascobank.autenticacao.controllers;


import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vascobank.autenticacao.dto.LoginFormDTO;
import com.vascobank.autenticacao.dto.TokenDTO;
import com.vascobank.autenticacao.dto.UsuarioDTO;
import com.vascobank.autenticacao.exception.SenhaInvalidaException;
import com.vascobank.autenticacao.model.Usuario;
import com.vascobank.autenticacao.security.JwtService;
import com.vascobank.autenticacao.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autenticacao")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO registrar( @RequestBody @Valid Usuario usuario ) {

        if(usuarioService.isEmailNotUsed(usuario)){
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
            return UsuarioDTO.converter(usuarioService.salvar(usuario));
        }
        return new UsuarioDTO();
    }

    @PostMapping("/autenticar")
    public TokenDTO autenticar(@RequestBody LoginFormDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .email(credenciais.getEmail())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getEmail(), "Bearer", token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/detalhar")
    @SecurityRequirement(name = "bearer-key")
    public Usuario detalhar(Principal principal) {
        Usuario usuario = usuarioService.findByEmail(principal.getName());
        return usuario;
    }

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
