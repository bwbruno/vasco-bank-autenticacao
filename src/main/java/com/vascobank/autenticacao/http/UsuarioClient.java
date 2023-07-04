package com.vascobank.autenticacao.http;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vascobank.autenticacao.dto.PageDTO;
import com.vascobank.autenticacao.dto.UsuarioDTO;

@FeignClient(name = "UsuarioClient", url = "localhost:7005/api/usuarios")
public interface UsuarioClient {

    @RequestMapping(method = RequestMethod.GET)
    List<UsuarioDTO> getUsuarios();

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    UsuarioDTO getUsuarioById(@PathVariable("id") int id);
    
    @RequestMapping(method = RequestMethod.GET, value = "?email={email}&size=1", consumes = "application/json")
    PageDTO<UsuarioDTO> getUsuarioByEmail(@PathVariable("email") String email);
    
}
