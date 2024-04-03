package com.example.ejercicioTrivia.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "question")
    private String question;
    @Column(name = "options")
    private String options;
    @Column(name = "answer")
    private Integer answer;
    @Column(name = "explanation")
    private String explanation;

    public Question(){}



}
