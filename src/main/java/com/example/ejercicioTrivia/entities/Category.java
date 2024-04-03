package com.example.ejercicioTrivia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Question> questions;
//    Como la clase Question tiene una relación @ManyToOne con Category, la serialización puede
//    estar causando un ciclo infinito al intentar serializar la lista de preguntas en Category que
//    a su vez contiene las preguntas, lo que resulta en un error al intentar enviar la respuesta
//    al cliente.
//    la anotación @JsonIgnore en la propiedad questions de la clase Category evita que se serialice
//    y así romper el ciclo de serialización
    public Category() {}

}
