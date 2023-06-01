package com.vascobank.autenticacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutenticarDTO {
    private String email;
    private String tipo;
    private String token;
}
