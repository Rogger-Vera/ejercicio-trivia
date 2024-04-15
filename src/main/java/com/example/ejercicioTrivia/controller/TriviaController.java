package com.example.ejercicioTrivia.controller;

import com.example.ejercicioTrivia.entities.Category;
import com.example.ejercicioTrivia.entities.Question;
import com.example.ejercicioTrivia.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
public class TriviaController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{category}")
    public ResponseEntity<?> getQuestionByCategory(@PathVariable String category) throws JsonProcessingException {
        Question question = questionService.getRandomQuestionByCategory(category);
        if (question != null) {
            ObjectMapper mapper = new ObjectMapper();
            String[] optionsArray = mapper.readValue(question.getOptions(), String[].class);

            Map<String, Object> response = new HashMap<>();
            response.put("category", question.getCategory().getName());
            response.put("question", question.getQuestion());
            response.put("options", optionsArray);
            response.put("answer", question.getAnswer());
            response.put("explanation", question.getExplanation());

            return ResponseEntity.ok(response);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return questionService.getAllCategories();

    }
}
