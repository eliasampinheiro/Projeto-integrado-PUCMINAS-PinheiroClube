package com.elias.club.model;


import com.elias.club.model.dto.UsuarioCadastroDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String password;
    private Tipo tipo = Tipo.USUARIO;
    @OneToOne
    private Carteira carteira;
    public Usuario(UsuarioCadastroDto usuarioCadastroDto){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.name=usuarioCadastroDto.nome();
        this.email=usuarioCadastroDto.email();
        this.cpf = usuarioCadastroDto.cpf();
        this.password = usuarioCadastroDto.password();
        this.dataNascimento = LocalDate.parse(usuarioCadastroDto.dataNascimento(), dateTimeFormatter);
    }
}
