package com.uni.lu.bean;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

import com.uni.lu.entity.Author;
import com.uni.lu.repository.AuthorRepository;

@Named
@ViewScoped
public class AuthorBean implements Serializable {

    private Author newAuthor = new Author();

    @EJB
    private AuthorRepository authorRepository;

    public void createNewAuthor() {
        try {
            authorRepository.createAuthor(newAuthor);
            // Clear the form
            newAuthor = new Author();
        } catch (Exception e) {
            // ... (error handling)
        }
    }

    public Author getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

}
