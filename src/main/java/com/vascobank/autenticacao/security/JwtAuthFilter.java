package com.vascobank.autenticacao.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vascobank.autenticacao.model.Usuario;
import com.vascobank.autenticacao.service.UsuarioService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

//intercepta a requisição
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UsuarioService usuarioService;

    public JwtAuthFilter( JwtService jwtService, UsuarioService usuarioService ) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if( authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);

            if(isValid){
                String loginUsuario = jwtService.obterLoginUsuario(token);
                Usuario usuario = usuarioService.getUsuarioByEmail(loginUsuario);
                //coloca o usuario no contexto do spring security
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities());
                //autenticação web        
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //contexto do spring security
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }
        
        //passa a requisição com o usuário autenticado no contexto de segurança
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
