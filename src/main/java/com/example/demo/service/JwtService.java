package com.example.demo.service;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JwtService {

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    public Usuario listarUsuario(UUID id) {
        Optional<Usuario> usuario = iUsuarioRepository.findById(id);

        if(usuario.isPresent()){
            return usuario.get();
        }else {
            return null;
        }
    }
}
