package com.vascobank.autenticacao.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.vascobank.autenticacao.dto.PapelDTO;
import com.vascobank.autenticacao.dto.PapelFormDTO;
import com.vascobank.autenticacao.model.Papel;
import com.vascobank.autenticacao.service.PapelService;

@RestController
@RequestMapping("/autenticacao")
public class PapelController {

    @Autowired
    PapelService papelService;

    @GetMapping("/papeis")
    public List<PapelDTO> find() {
        List<Papel> papeis = papelService.getListPapel();
        return PapelDTO.converter(papeis);
    }

    @PostMapping("/papel")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PapelDTO> save(@RequestBody @Valid PapelFormDTO form, UriComponentsBuilder uriBuilder) {
        Papel papel = form.converter();
        papelService.savePapel(papel);

        URI uri = uriBuilder.path("/papeis/{id}").buildAndExpand(papel.getId()).toUri();
        return ResponseEntity.created(uri).body(new PapelDTO(papel));
    }

    @PutMapping("/papel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody PapelFormDTO form) {
        try {
            Papel papel = form.converter();
            papelService.atualizarPapel(id, papel.getNome());
        } catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado");
        }
    }

}
