package com.vascobank.autenticacao.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.vascobank.autenticacao.dto.PageDTO;
import com.vascobank.autenticacao.dto.UsuarioDTO;
import com.vascobank.autenticacao.exception.RegraNegocioException;
import com.vascobank.autenticacao.http.UsuarioClient;
import com.vascobank.autenticacao.model.Papel;
import com.vascobank.autenticacao.model.Usuario;
import com.vascobank.autenticacao.repository.UsuarioRepository;

@Component
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Transactional
    @Override
    public Usuario salvar(Usuario usuario) {
        
        if(this.isEmailNotUsed(usuario)){
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
        }

        return repository.save(usuario);
    }

    @Override
    public Boolean isEmailNotUsed(Usuario usuario) {
        Optional<Usuario> a = repository.findByEmail(usuario.getEmail());
        if(!a.isPresent()){
            return true;
        }

        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email já cadastrado");
    }

    @Transactional
    @Override
    public Usuario atribuirPapel(Integer id, Papel papel) {
        Usuario usuario = getUsuarioById(id);
        usuario.getPapeis().clear();
        usuario.getPapeis().add(papel);
        return repository.save(usuario);
    }

    @Override
    public UserDetails autenticar( Usuario usuario ){
        PageDTO<UsuarioDTO> usuarioDTO = usuarioClient.getUsuarioByEmail("maria@email.com");
        System.out.println(usuarioDTO);

        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasBatem = passwordEncoder.matches( usuario.getSenha(), user.getPassword() );

        if(senhasBatem){
            return user;
        }

        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Senha inválida");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(email);
		if (usuario.isPresent()) {
			return usuario.get();
		}

        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email inválido");
    }

    @Override
    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
		if (usuario.isPresent()) {
			return usuario.get();
		}

       throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email inválido");
    }

    @Override
    public void removeUsuario(Usuario usuario) {
        repository.delete(usuario);
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return repository
                    .findById(id)
                    .map(user -> {
                        return user;
                    })
                    .orElseThrow(
                        () -> new RegraNegocioException("Usuário " + id + " não foi encontrado")
                    );
    }

    @Override
    public List<Usuario> getListUsuario() {


        return repository.findAll();
    }

    @Override
    public Usuario atribuirPapelPorEmail(String email, Papel papel) {
        Usuario usuario = findByEmail(email);
        usuario.getPapeis().add(papel);
        return repository.save(usuario);
    }
}
