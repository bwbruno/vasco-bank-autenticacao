package com.vascobank.autenticacao.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vascobank.autenticacao.security.JwtAuthFilter;
import com.vascobank.autenticacao.security.JwtService;
import com.vascobank.autenticacao.service.UsuarioServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and().cors()
            .and().csrf().disable()
            .authorizeHttpRequests((authz) -> {
                try {
                    authz
                        .antMatchers("/v3/api-docs/**").permitAll()

                        .antMatchers("/swagger-ui.html").permitAll()

                        .antMatchers("/swagger-ui/**").permitAll()

                        .antMatchers("/autenticacao/registrar")
                            .permitAll()

                        .antMatchers("/autenticacao/autenticar")
                            .permitAll()

                        .antMatchers("/autenticacao/usuario/**")
                            .hasAnyRole("USER")

                        .antMatchers("/autenticacao/usuarios")
                            .hasAnyRole("USER")

                        .anyRequest().permitAll()
                        .and() 
                            .sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        return http.build();
    }
}
