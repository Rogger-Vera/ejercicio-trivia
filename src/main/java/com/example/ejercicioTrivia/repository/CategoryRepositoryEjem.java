package com.example.ejercicioTrivia.repository;

import com.example.ejercicioTrivia.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CategoryRepositoryEjem {
    @PersistenceContext
    EntityManager entityManager;

    public Category findById(Long id){
        return entityManager.find(Category.class, id);
    }

    public Category findByName(String name){
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Category c WHERE c.name = :name", Category.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Manejo de caso en que no se encuentra ninguna categoría con ese nombre
        }
    }

}
