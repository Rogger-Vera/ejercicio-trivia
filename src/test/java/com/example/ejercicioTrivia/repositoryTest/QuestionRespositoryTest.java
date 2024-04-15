package com.example.ejercicioTrivia.repositoryTest;

import com.example.ejercicioTrivia.entities.Category;
import com.example.ejercicioTrivia.entities.Question;
import com.example.ejercicioTrivia.repository.CategoryRepository;
import com.example.ejercicioTrivia.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class QuestionRespositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenFindByCategoryNameThenReturnQuestions(){
        Category category1 = Category.builder()
                .id(1L)
                .name("Cultura")
                .description("Descubre y aprende sobre las expresiones artísticas, tradiciones, costumbres y conocimientos de diversas culturas alrededor del mundo.")
                .questions(null)
                .build();
        categoryRepository.save(category1);

        Question question1 = Question.builder()
                .question("¿Cuál es el baile nacional de Argentina?")
                .answer(0)
                .options("[\"Tango\", \"Samba\", \"Flamenco\"]")
                .category(category1)
                .explanation("El tango es el baile nacional de Argentina.")
                .build();
        Question question2 = Question.builder()
                .question("¿En qué año comenzó la Primera Guerra Mundial?")
                .answer(0)
                .options("[\"1914\", \"1939\", \"1945\"]")
                .category(category1)
                .explanation("La Primera Guerra Mundial comenzó en 1914.")
                .build();
        questionRepository.save(question1);
        questionRepository.save(question2);

        List<Question> questionList = questionRepository.findByCategoryName(category1.getName());

        assertThat(questionList).isNotNull();
        assertEquals(2, questionList.size());

    }

    @Test
    public void whenFindByCategoryName_thenReturnEmptyList() {
        List<Question> questions = questionRepository.findByCategoryName("NonExistentCategory");

        assertEquals(0, questions.size());
    }

}
