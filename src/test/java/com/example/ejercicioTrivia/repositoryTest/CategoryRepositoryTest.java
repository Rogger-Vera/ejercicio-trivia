package com.example.ejercicioTrivia.repositoryTest;

import com.example.ejercicioTrivia.entities.Category;
import com.example.ejercicioTrivia.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenFindAllThenReturnAllCategories() {
        Category category1 = Category.builder()
                .id(1L)
                .name("Cultura")
                .description("Descubre y aprende sobre las expresiones artísticas, tradiciones, costumbres y conocimientos de diversas culturas alrededor del mundo.")
                .questions(null)
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .name("Deportes")
                .description("Sumérgete en el mundo del deporte, conoce sus historias, jugadores destacados, equipos legendarios y eventos más emocionantes.")
                .questions(null)
                .build();
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findAll();

        assertThat(categoryList).isNotNull();
        assertEquals(2, categoryList.size());

    }

    @Test
    public void whenFindAllThenReturnEmptyList() {
        List<Category> categoryList = categoryRepository.findAll();

        assertEquals(0, categoryList.size());
    }
}
