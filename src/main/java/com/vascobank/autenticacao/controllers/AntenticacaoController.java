package com.vascobank.autenticacao.controllers;


import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vascobank.autenticacao.dto.AutenticarDTO;
import com.vascobank.autenticacao.dto.LoginDTO;
import com.vascobank.autenticacao.dto.QuemSouEuDTO;
import com.vascobank.autenticacao.dto.TokenDTO;
import com.vascobank.autenticacao.model.Usuario;
import com.vascobank.autenticacao.security.JwtService;
import com.vascobank.autenticacao.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autenticacao")
public class AntenticacaoController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping("/autenticar")
    public ResponseEntity<AutenticarDTO> autenticar(@RequestBody LoginDTO loginDTO){

        usuarioService.autenticar(loginDTO);

        Usuario usuario = usuarioService.getUsuarioByEmail(loginDTO.getEmail());
        String token = jwtService.gerarToken(usuario);
        var tokenDTO = new AutenticarDTO(usuario.getEmail(), "Bearer", token);

        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validar(@RequestBody TokenDTO tokenDTO){

        if(tokenDTO.getTipo().equalsIgnoreCase("Bearer") && jwtService.tokenValido(tokenDTO.getToken())){
            return ResponseEntity.ok(true);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @GetMapping("/quemsoueu")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<QuemSouEuDTO> detalhar(Principal principal) {
        Usuario usuario = usuarioService.getUsuarioByEmail(principal.getName());
        return ResponseEntity.ok(new QuemSouEuDTO(usuario));
    }

}
