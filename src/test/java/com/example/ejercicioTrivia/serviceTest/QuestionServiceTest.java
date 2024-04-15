package com.example.ejercicioTrivia.serviceTest;

import com.example.ejercicioTrivia.entities.Category;
import com.example.ejercicioTrivia.entities.Question;
import com.example.ejercicioTrivia.repository.CategoryRepository;
import com.example.ejercicioTrivia.repository.QuestionRepository;
import com.example.ejercicioTrivia.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    public void getRandomQuestionByCategoryReturnsQuestionWhenQuestionsExist(){
        Category category1 = Category.builder()
                .id(1L)
                .name("Cultura")
                .description("Descubre y aprende sobre las expresiones artísticas, tradiciones, costumbres y conocimientos de diversas culturas alrededor del mundo.")
                .questions(null)
                .build();
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
        List<Question> questionList = List.of(question1, question2);

        given(questionRepository.findByCategoryName(category1.getName())).willReturn(questionList);

        Question questionResult = questionService.getRandomQuestionByCategory(category1.getName());

        assertThat(questionList).isNotNull();
        assertEquals("Cultura", questionResult.getCategory().getName());
    }

    @Test
    public void getRandomQuestionByCategoryThrowsExceptionWhenNoQuestionsExist() {
        given(questionRepository.findByCategoryName(anyString())).willReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> questionService.getRandomQuestionByCategory("NonExistentCategory"));
    }

    @Test
    public void getAllCategoriesReturnsCategoriesWhenCategoriesExist() {
        List<Category> listCategories = Arrays.asList(
                Category.builder().id(1L).name("Category1").description("Description1").build(),
                Category.builder().id(2L).name("Category2").description("Description2").build()
        );

        given(categoryRepository.findAll()).willReturn(listCategories);

        List<Category> resultListCategories = questionService.getAllCategories();

        assertThat(resultListCategories).isNotNull();
        assertEquals(2, resultListCategories.size());
        assertEquals("Category1", resultListCategories.get(0).getName());
        assertEquals("Category2", resultListCategories.get(1).getName());
    }

    @Test
    public void getAllCategories_ReturnsEmptyList_WhenNoCategoriesExist() {
        given(categoryRepository.findAll()).willReturn(Collections.emptyList());

        List<Category> resultListCategories = questionService.getAllCategories();

        assertThat(resultListCategories).isNotNull();
        assertTrue(resultListCategories.isEmpty());
    }

}
