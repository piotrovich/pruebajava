package com.example.demo.controller;

import com.example.demo.entity.Usuario;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.JwtService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtService jwtService;

    @PostMapping("/insertar-usuario")
    public ResponseEntity<?> save(@RequestBody Usuario usuario){
        return usuarioService.insertarUsuario(usuario);
    }

    @GetMapping("/listar-usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios(@RequestHeader(value = "Authorization") String token){
        return usuarioService.listarUsuarios(token);
    }
}
