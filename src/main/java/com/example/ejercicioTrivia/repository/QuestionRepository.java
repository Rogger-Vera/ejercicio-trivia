package com.example.ejercicioTrivia.repository;

import com.example.ejercicioTrivia.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryName(String categoryName);
}
