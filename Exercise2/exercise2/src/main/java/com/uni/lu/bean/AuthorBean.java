package com.uni.lu.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import com.uni.lu.entity.Author;
import com.uni.lu.repository.AuthorRepository;

@Named
@ViewScoped
public class AuthorBean implements Serializable {

    private Author newAuthor = new Author();

    @EJB
    private AuthorRepository authorRepository;
    private List<Author> allAuthors;
    private boolean editMode = false;
    private String updatedName;


    public void createNewAuthor() {
        try {
            authorRepository.createAuthor(newAuthor);
            // Clear the form
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", "<br/>Author '" + newAuthor.getName() + "' created."));
                newAuthor = new Author();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Failed to create new author '" + newAuthor.getName() + "'."));
            e.printStackTrace(); // Log the exception for debugging
        }
    }
    
    @PostConstruct
    public void init(){
        allAuthors = authorRepository.findAllAuthors();
    }

    public void updateAuthor(Author author) {
        authorRepository.updateAuthor(author);
    }
    
    public void deleteAuthor(Author author) {
        try {
            authorRepository.deleteAuthor(author);
            allAuthors.remove(author);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", "<br/>Author '" + author.getName() + "' deleted."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Failed to delete author '" + author.getName() + "'."));
            e.printStackTrace(); // Log the exception for debugging
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

    public List<Author> getAllAuthors(){
        return allAuthors;
    }

    public void toggleEditMode() {
        editMode = !editMode;
    }

    public void updateAuthor(Author author, String updatedName) {
        author.setName(updatedName);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    public String goBack() {
        return "back"; 
    }
    
    public String goToIndex() {
        return "index"; 
    }
}
