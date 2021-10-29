package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.intellij.lang.annotations.Pattern;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "tbl_usuario")
@Data
@Entity
@Getter
@Setter
@ToString
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private String correo;
    private String pass;

    @Embedded
    private Telefono telefono;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    public Usuario(){}

}
