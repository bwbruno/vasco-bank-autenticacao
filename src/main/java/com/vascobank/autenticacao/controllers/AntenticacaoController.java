package com.vascobank.autenticacao.controllers;


import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.vascobank.autenticacao.dto.AutenticarDTO;
import com.vascobank.autenticacao.dto.LoginDTO;
import com.vascobank.autenticacao.dto.RegistroDTO;
import com.vascobank.autenticacao.dto.TokenDTO;
import com.vascobank.autenticacao.dto.UsuarioDTO;
import com.vascobank.autenticacao.model.Usuario;
import com.vascobank.autenticacao.security.JwtService;
import com.vascobank.autenticacao.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autenticacao")
public class AntenticacaoController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody @Valid RegistroDTO registroDTO) {
        Usuario usuario = Usuario.builder()
                                    .nome(registroDTO.getNome())
                                    .email(registroDTO.getEmail())
                                    .senha(registroDTO.getSenha())
                                    .build();

        var usuarioDTO = UsuarioDTO.converter(usuarioService.salvar(usuario));
        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<AutenticarDTO> autenticar(@RequestBody LoginDTO loginDTO){
        Usuario usuario = Usuario.builder()
                                    .email(loginDTO.getEmail())
                                    .senha(loginDTO.getSenha())
                                    .build();

        usuarioService.autenticar(usuario);

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

    @GetMapping("/usuario")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioDTO> detalhar(Principal principal) {
        var usuario = usuarioService.findByEmail(principal.getName());
        var usuarioDTO = UsuarioDTO.converter(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

}
