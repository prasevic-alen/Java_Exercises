package com.uni.lu.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.uni.lu.entity.Author;
import com.uni.lu.entity.Document;
import com.uni.lu.repository.DocumentRepository;
import com.uni.lu.repository.AuthorRepository;

@Named
@ViewScoped
public class AssignAuthorBean implements Serializable {

    @EJB
    private DocumentRepository documentRepository;
    @EJB
    private AuthorRepository authorRepository;

    private List<Document> documents;
    private List<Author> availableAuthors;
    private Integer selectedDocumentId;
    private Integer selectedAuthorId;

    @PostConstruct
    public void init() {
        try {
            documents = documentRepository.findAllDocuments();
            availableAuthors = authorRepository.findAllAuthors();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (e.g., display error message)
        }
    }

    public String assignAuthor() {
        Document document = documentRepository.findDocumentById(selectedDocumentId);
        Author author = authorRepository.findAuthorById(selectedAuthorId);
        try {
            if (selectedDocumentId != null && selectedAuthorId != null) {

                if (document != null && author != null) {
                    // Update document's author association (logic depends on your entity relationships)
                    document.getAuthors().add(author); // Assuming a ManyToMany relationship

                    documentRepository.updateDocument(document);

                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", "<br/>Author '" + author.getName() + "' assigned to document '" + document.getTitle() + "'."));
                    return "authors"; // Navigate back to the document list
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Invalid document or author selection."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Please select a document and an author."));
            }
        } catch (EJBTransactionRolledbackException ex) {
            Throwable cause = getRootCause(ex);
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Foreign key constraint violation!<br/>Author '" + author.getName() + "' is already assigned to document '" + document.getTitle() +"'."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Failed to assign author."));
            }
            // ex.printStackTrace(); // Log the exception for debugging
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Failed to assign author."));
            // e.printStackTrace(); // Log the exception for debugging
        }
        return null; // Shouldn't normally reach here, but added for completeness
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = null;
        Throwable result = throwable;

        while (result != null && result.getCause() != null && result != result.getCause()) {
            cause = result.getCause();
            result = result.getCause();
        }
        return cause;
    }

    public String goBack() {
        // Consider using navigation rules or a dedicated back action in your main document bean
        return "/index?faces-redirect=true"; // Navigate back to the document list (replace with appropriate navigation rule)
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Author> getAvailableAuthors() {
        return availableAuthors;
    }

    public void setAvailableAuthors(List<Author> availableAuthors) {
        this.availableAuthors = availableAuthors;
    }

    public Integer getSelectedDocumentId() {
        return selectedDocumentId;
    }

    public void setSelectedDocumentId(Integer selectedDocumentId) {
        this.selectedDocumentId = selectedDocumentId;
    }

    public Integer getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Integer selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }


}


