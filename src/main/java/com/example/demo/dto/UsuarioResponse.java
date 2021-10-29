package com.example.demo.dto;

import com.example.demo.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class UsuarioResponse {

    private String id;
    private Date created;
    private Date modified;
    private Date last_login;
    private String token;
    private String isActive;

    public UsuarioResponse(){}

}
