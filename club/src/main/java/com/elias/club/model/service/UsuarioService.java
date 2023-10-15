package com.elias.club.model.service;

import com.elias.club.model.Usuario;
import com.elias.club.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario cadastrar(Usuario usuario){
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Transactional
    public Usuario atualizar(Usuario usuario){
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
}
