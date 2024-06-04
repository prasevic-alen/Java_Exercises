package com.uni.lu.bean;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;

import com.uni.lu.entity.Author;
import com.uni.lu.entity.Document;
import com.uni.lu.repository.*;

@Named
@ViewScoped
public class DocumentBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private DocumentRepository documentRepository;

    private List<Document> documents;
    private Document selectedDocument;
    private int selectedYear;
    private Integer selectedDocumentId;
    private Integer selectedAuthorId;
    private Integer documentIdToView;

    private Document newDocument = new Document(); // For creating new documents
    private AuthorRepository authorRepository;
    private List<Author> availableAuthors; // To display authors for selection
    private List<Integer> selectedAuthorIds; // Store selected author IDs


    @PostConstruct
    public void init() {
        try {
            documents = documentRepository.findAllDocuments();
            availableAuthors = authorRepository.findAllAuthors();
            selectedAuthorIds = new ArrayList<>();  // Initialize the list
        } catch (Exception e) {
            e.printStackTrace();
            // To handle the exception
        }

    }

    public void searchDocumentsByYear() {
        try {
            documents = documentRepository.findDocumentsByPublicationYear(selectedYear);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public void loadDocumentDetails() {
        try {
            if (selectedDocumentId != null) {
                Hibernate.initialize(selectedDocument.getAuthors());
                selectedDocument = documentRepository.findDocumentById(selectedDocumentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // To handle the exception
        }
    }

        public void viewDocumentDetails() { // Modified method
        try {
            if (documentIdToView != null) {
                selectedDocument = documentRepository.findDocumentById(documentIdToView);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid document ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public void createNewDocument() {
        try {
            if (selectedAuthorId != null) { 
                Author selectedAuthor = authorRepository.findAuthorById(selectedAuthorId);
                if (selectedAuthor != null) {
                    newDocument.setAuthors(Collections.singletonList(selectedAuthor)); // Assuming you are adding a single author for now
                    documentRepository.createDocument(newDocument);

                    // Clear the form
                    newDocument = new Document();
                    selectedAuthorId = null; // Reset the selected author ID
                } else {
                    // Handle the case where author is not found
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid author selected."));
                }
            } else {
                // Handle the case where no author was selected
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select an author."));
            }
        } catch (Exception e) {
            // Handle exceptions
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while creating the document."));
        }
    }

    public String goBack() {
        this.selectedDocument = null;
        this.selectedDocumentId = null;
        String currentViewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if (currentViewId == null) {
            currentViewId = "index"; // Default to index if no parameter found
        }
        return currentViewId + "?faces-redirect=true";
    }

    // Getters and Setters
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public Integer getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(Integer selectedYear) {
        this.selectedYear = selectedYear;
    }

    public Integer getSelectedDocumentId() {
        return selectedDocumentId;
    }

    public void setSelectedDocumentId(Integer selectedDocumentId) {
        this.selectedDocumentId = selectedDocumentId;
    }

    public Integer getDocumentIdToView() {
        return documentIdToView;
    }

    public void setDocumentIdToView(Integer documentIdToView) {
        this.documentIdToView = documentIdToView;
    }

    public Document getNewDocument() {
        return newDocument;
    }

    public void setNewDocument(Document newDocument) {
        this.newDocument = newDocument;
    }
    
    public List<Author> getAvailableAuthors() {
        return availableAuthors;
    }

    public List<Integer> getSelectedAuthorIds() {
        return selectedAuthorIds;
    }

    public void setSelectedAuthorIds(List<Integer> selectedAuthorIds) {
        this.selectedAuthorIds = selectedAuthorIds;
    }

    public Integer getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Integer selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }

    
}
