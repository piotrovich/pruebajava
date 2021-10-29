package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {

    private String codigo;
    private String mensaje;
    private String token;

    public ErrorResponse(){}
}
