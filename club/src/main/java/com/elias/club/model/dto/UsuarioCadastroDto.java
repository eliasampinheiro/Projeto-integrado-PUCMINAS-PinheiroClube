package com.elias.club.model.dto;

import java.time.LocalDate;

public record UsuarioCadastroDto(String nome, String dataNascimento, String email, String cpf, String password) {
}
