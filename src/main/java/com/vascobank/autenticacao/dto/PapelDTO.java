package com.vascobank.autenticacao.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.vascobank.autenticacao.model.Papel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PapelDTO {
    private Long id;
    private String nome;

    public PapelDTO(Papel permissao) {
        this.id = permissao.getId();
        this.nome = permissao.getNome();
    }

    public static List<PapelDTO> converter(List<Papel> permissoes) {
        return permissoes.stream().map(PapelDTO::new).collect(Collectors.toList());
    }
}
