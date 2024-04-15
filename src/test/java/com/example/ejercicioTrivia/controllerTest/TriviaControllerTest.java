package com.example.ejercicioTrivia.controllerTest;

import com.example.ejercicioTrivia.controller.TriviaController;
import com.example.ejercicioTrivia.entities.Category;
import com.example.ejercicioTrivia.entities.Question;
import com.example.ejercicioTrivia.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TriviaController.class)
public class TriviaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    public void getQuestionByCategoryReturnsQuestionResponseWhenQuestionExists() throws Exception{
        Category category = Category.builder()
                .id(1L)
                .name("Cultura")
                .description("Descubre y aprende sobre las expresiones artísticas, tradiciones, costumbres y conocimientos de diversas culturas alrededor del mundo.")
                .questions(null)
                .build();

        Question question = Question.builder()
                .question("¿Cuál es el baile nacional de Argentina?")
                .answer(0)
                .options("[\"Tango\", \"Samba\", \"Flamenco\"]")
                .category(category)
                .explanation("El tango es el baile nacional de Argentina.")
                .build();

        given(questionService.getRandomQuestionByCategory(category.getName())).willReturn(question);

        ResultActions resultActions = mockMvc.perform(get("/question/{category}", category.getName()));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.question").value(question.getQuestion()))
                .andExpect(jsonPath("$.answer").value(question.getAnswer()));

    }

    @Test
    public void getQuestionByCategoryReturnsNotFoundWhenQuestionDoesNotExist() throws Exception {

        given(questionService.getRandomQuestionByCategory(anyString())).willReturn(null);

        mockMvc.perform(get("/question/Cultura"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllCategoriesThenResponseOk() throws Exception {

        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category(1L, "Category1", "Description1", null);
        Category category2 = new Category(2L, "Category2", "Description2", null);
        given(questionService.getAllCategories()).willReturn(categoryList);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categoryList.size()));
    }

    @Test
    public void getAllCategories_ReturnsEmptyList_WhenNoCategoriesExist() throws Exception {
        given(questionService.getAllCategories()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(questionService, times(1)).getAllCategories();
    }

}
