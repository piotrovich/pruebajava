package com.example.demo.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class Telefono {

    private String numero;
    private String codCiudad;
    private String codPais;

    public Telefono(){}
}
