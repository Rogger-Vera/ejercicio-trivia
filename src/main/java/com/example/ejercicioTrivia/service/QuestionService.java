package com.example.ejercicioTrivia.service;

import com.example.ejercicioTrivia.entities.Category;
import com.example.ejercicioTrivia.entities.Question;
import com.example.ejercicioTrivia.repository.CategoryRepository;
import com.example.ejercicioTrivia.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Question getRandomQuestionByCategory(String category){
        System.out.println("Categoria: " + category);
        List<Question> questions = questionRepository.findByCategoryName(category);
        if (questions.isEmpty()){
            throw new RuntimeException("No hay preguntas disponibles para la categoria " + category);
        }
        int randomIndex = (int) (Math.random() * questions.size());
        return questions.get(randomIndex);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
