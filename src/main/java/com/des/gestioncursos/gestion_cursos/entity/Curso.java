package com.des.gestioncursos.gestion_cursos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Jpa e hibernate permite mapear
@Entity
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Curso {

    @Id //indica que será el campo id de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Length -> maximo 128, nullable = false -> no puede ser nulo
    @Column(length = 128, nullable = false)
    private String titulo;

    @Column(length = 256)
    private String descripcion;

    @Column(nullable = false) //No puede recibir valores nulos
    private int nivel;

    @Column(name = "estado_publicacion")
    private boolean isPublicado;
}
