package com.uni.lu.repository;

import com.uni.lu.entity.Author;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collections;
import java.util.List;

@Stateless
public class AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Author> findAllAuthors() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    public void createAuthor(Author author) {
        em.persist(author);
    }

    public void updateAuthor(Author author) {
        em.merge(author);
    }

    public void deleteAuthor(Author author) {
        // Ensure the author is managed by the persistence context before deleting
        author = em.merge(author); 
        em.remove(author);
    }

    public List<Author> findAuthorsByIds(List<Integer> list) {
        if (list == null ) {
            return Collections.emptyList(); // Return empty list if no IDs are provided
        }
        
        return em.createQuery("SELECT a FROM Author a WHERE a.id IN :authorIds", Author.class)
                .setParameter("authorIds", list)
                .getResultList();
    }

    public Author findAuthorById(Integer authorId) {  // Changed parameter type to Integer
        return em.find(Author.class, authorId);
    }
}
