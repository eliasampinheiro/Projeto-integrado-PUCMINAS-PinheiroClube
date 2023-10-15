package com.elias.club;

import com.elias.club.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ClubApplication {
	private static Usuario usuario;

	public static void main(String[] args) {
		SpringApplication.run(ClubApplication.class, args);

	}

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		ClubApplication.usuario = usuario;
	}
}
