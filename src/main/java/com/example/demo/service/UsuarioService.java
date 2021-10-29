package com.example.demo.service;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.IUsuarioRepository;
import com.example.demo.util.ValidaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    ValidaUtil validaUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtService jwtService;


    public ResponseEntity<?> insertarUsuario(Usuario usuario) {

        boolean valido = validaUtil.validaEmail(usuario.getCorreo());

        if(valido == false){
            ErrorResponse error = new ErrorResponse();
            error.setCodigo("Error");
            error.setMensaje("El correo ingresado no cumple con formato requerido ej: aaaaaa@dominio.cl");
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        boolean validaCorreoDb = validaUtil.validaEmailDb(usuario.getCorreo());

        if (validaCorreoDb){
            ErrorResponse error = new ErrorResponse();
            error.setCodigo("Error");
            error.setMensaje("El correo ingresado ya existe en la base de datos, favor ingrese otro");
            return new ResponseEntity(error, HttpStatus.NOT_ACCEPTABLE);
        }

        boolean validaPass = validaUtil.validaPass(usuario.getPass());

        if (!validaPass){
            ErrorResponse error = new ErrorResponse();
            error.setCodigo("Error");
            error.setMensaje("La contraseña no cumple con el formato requerido ej: Aaaaa11");
            return new ResponseEntity(error, HttpStatus.NOT_ACCEPTABLE);
        }

        try{

            Usuario usuario1 = iUsuarioRepository.save(usuario);
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setId(String.valueOf(usuario1.getId()));
            usuarioResponse.setCreated(usuario1.getFechaCreacion());
            usuarioResponse.setModified(usuario1.getFechaActualizacion());
            usuarioResponse.setToken(token(usuario1));
            usuarioResponse.setIsActive("Activo");
            usuarioResponse.setLast_login(usuario1.getFechaCreacion());
            return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setCodigo("500");
            error.setMensaje("Error al ingresar usuario");
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Usuario>> listarUsuarios(@RequestHeader(value = "Authorization") String token) {

        String usuarioId = jwtUtil.getKey(token);
        if(usuarioId == null){
            ErrorResponse response = new ErrorResponse();
            response.setCodigo("Error");
            response.setMensaje("La sesion de usuario no es valida");
            return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
        }
        try{
            List<Usuario> list = iUsuarioRepository.findAll();
            if(list.isEmpty() || list.size() == 0){
                return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
        }catch (Exception e){
            ErrorResponse error = new ErrorResponse();
            error.setCodigo("500");
            error.setMensaje("Error al listar usuarios");
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String token(Usuario usuario) {
        Usuario usuarioLog = jwtService.listarUsuario(usuario.getId());

        if(usuarioLog != null){
            String token = jwtUtil.create(String.valueOf(usuarioLog.getId()), usuarioLog.getNombre());
            return token;
        }else {
            return null;
        }
    }
}
