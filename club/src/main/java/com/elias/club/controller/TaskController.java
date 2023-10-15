package com.elias.club.controller;


import com.elias.club.ClubApplication;
import com.elias.club.model.Tipo;
import com.elias.club.model.Usuario;
import com.elias.club.model.dto.UsuarioCadastroDto;
import com.elias.club.model.dto.UsuarioLoginDTO;
import com.elias.club.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TaskController {

    @Autowired
    UsuarioService usuarioService;

    //views
    @GetMapping("/cadastro")
    public String cadastro(){
        return "cadastro";
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/informativo")
    public String informativo(){
        return "informativo";
    }
    @GetMapping("/sistemaAdm")
    public String adm(){
        if (ClubApplication.getUsuario().getTipo().equals(Tipo.ADMINISTRADOR)){
            return "sistemaAdm";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/sistemaUsuario")
    public String user(){
        return "sistemaUsuario";
    }

    @GetMapping("/liberarCarteiras")
    public String liberarCarteiras(){
        return "liberarCarteiras";
    }
    @GetMapping("/enviarNoticias")
    public String enviarNoticias(){
        return "enviarNoticias";
    }
    @GetMapping("/verificarStatus")
    public String verificarStatus(){
        return "verificarStatus";
    }
    @GetMapping("/solicitarCarteira")
    public String solicitarCarteira(){
        return "solicitarCarteira";
    }
    @GetMapping("/quadroInformacoes")
    public String quadroInformacoes(){
        return "quadroInformacoes";
    }
    @GetMapping("/emitirCarteira")
    public String emitirCarteira(){
        return "emitirCarteira";
    }

    @GetMapping("/emitirBoleto")
    public String emitirBoleto(){
        return "emitirBoleto";
    }

    @PostMapping("/registrar")
    public String cadastrar(UsuarioCadastroDto usuarioCadastroDto){
        Usuario usuario = new Usuario(usuarioCadastroDto);
        usuarioService.cadastrar(usuario);

        return "redirect:/";
    }
















    @PostMapping("/login")
    public String login(UsuarioLoginDTO usuarioDTO){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioService.findAll();

        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(usuarioDTO.cpf())){
                ClubApplication.setUsuario(usuario);
            }
        }
        if (ClubApplication.getUsuario().getPassword().equals(usuarioDTO.password())){
            switch (ClubApplication.getUsuario().getTipo()){
                case USUARIO:
                    return "redirect:/sistemaUsuario";
                case ADMINISTRADOR:
                    return "redirect:/sistemaAdm";

            }
        }else {
            System.out.println("senha nao confere");
        }
        return "index";

    }

}
