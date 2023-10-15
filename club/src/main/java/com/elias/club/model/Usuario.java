package com.elias.club.model;


import com.elias.club.model.dto.UsuarioCadastroDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

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
    private Tipo tipo;
    @OneToOne(cascade = CascadeType.ALL )
    private Carteira carteira;
    @OneToOne(cascade = CascadeType.ALL)
    private Boleto boleto;
    public Usuario(UsuarioCadastroDto usuarioCadastroDto){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.name=usuarioCadastroDto.nome();
        this.email=usuarioCadastroDto.email();
        this.cpf = usuarioCadastroDto.cpf();
        this.password = usuarioCadastroDto.password();
        this.dataNascimento = LocalDate.parse(usuarioCadastroDto.dataNascimento(), dateTimeFormatter);
    }
}
