package com.uni.lu.bean;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.uni.lu.entity.Author;
import com.uni.lu.entity.Document;
import com.uni.lu.repository.DocumentRepository;
import com.uni.lu.repository.AuthorRepository;

@Named
@ViewScoped
public class DocumentBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private DocumentRepository documentRepository;
    @EJB
    private AuthorRepository authorRepository;

    private List<Document> documents;
    private Document selectedDocument;
    private int selectedYear;
    private Integer selectedDocumentId;
    private Integer selectedAuthorId;
    private Integer documentIdToView;

    private Document newDocument = new Document(); // For creating new documents
    private List<Author> availableAuthors; // To display authors for selection
    private List<Integer> selectedAuthorIds; // Store selected author IDs
    private Boolean editing;
    private Boolean hideDocumentList = false;

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
                selectedDocument = documentRepository.findDocumentById(selectedDocumentId);
                if (selectedDocument != null) {
                    Hibernate.initialize(selectedDocument.getAuthors());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // To handle the exception
        }
    }

    public void viewDocumentDetails() {
        try {
            if (documentIdToView != null) {
                selectedDocument = documentRepository.findDocumentById(documentIdToView);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Invalid document ID."));
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
                    newDocument.setAuthors(Collections.singletonList(selectedAuthor));
    
                    // Date Handling:
                    if (newDocument.getPublicationDate() != null) {
                        // 1. Get the date at the start of the day in the user's time zone.
                        LocalDate localPublicationDate = newDocument.getPublicationDate().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate();
                        
                        // 2. Convert to Instant (always use UTC for persistence)
                        Instant publicationInstant = localPublicationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    
                        // 3. Set the Instant on the document (best practice for DB storage)
                        newDocument.setPublicationDate(Date.from(publicationInstant));  // Store in UTC
                    }
    
                    documentRepository.createDocument(newDocument);
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", "<br/>Document '" + newDocument.getTitle() + "' created.")); 
                    // Clear the form
                    newDocument = new Document(); 
                    selectedAuthorId = null; 
                    
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Invalid author selected for document '" + newDocument.getTitle() + "'."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Please select an author for document '" + newDocument.getTitle() + "'."));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>An error occurred while creating the document: '" + newDocument.getTitle() + "'.<br/>" + e.getMessage())); 
        }
    }
    
    @Transactional 
    public void loadDocumentForEdit() {
        try {
            if (documentIdToView != null) {
                selectedDocument = documentRepository.findDocumentById(documentIdToView);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Invalid document ID."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void viewDocumentDetails(Document document) {
        selectedDocument = documentRepository.findDocumentById(document.getId());
        editing = false; // Ensure view mode when viewing details
        hideDocumentList = true;
    }

    public void editDocument(Document document) {
        selectedDocument = documentRepository.findDocumentById(document.getId());
        editing = true; // Set to edit mode
        hideDocumentList = true;
    }
    
    @Transactional
    public String saveDocument() {
        editing = false;
        try {
            // Check if the selectedDocument is not null
            if (selectedDocument != null) {
                // Update the document in the repository
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", "<br/>Document '" + selectedDocument.getTitle() + "' saved."));
                documentRepository.updateDocument(selectedDocument);
                return "details"; // Navigate back to the document list
            } else {
                // Handle the case where the document was not loaded properly
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Failed to save document '" + selectedDocument.getTitle() + "'."));
                return null; // Stay on the edit page if there's an error
            }
        } catch (Exception e) {
            // Handle exceptions... (display error message)
            return null; 
        }
    }
    

    public Boolean getEditing() {
        return editing;
    }

    public String cancelEdit() {
        selectedDocument = null; // Clear the selected document
        return "index"; // Navigate back to the document list
    }

    public void deleteDocument(Document document) {
        try {
            // Delete the document from the repository
            documentRepository.deleteDocument(document);
            documents.remove(document); // Update the documents list

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success:", "<br/>Document deleted successfully."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "<br/>Failed to delete document."));
        }
    }

    public String goBack() {
        String referer = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");
    
        if (referer != null && referer.startsWith(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath())) {
            return referer + "?faces-redirect=true"; // Redirect to the referer URL
        } else {
            return "/index.xhtml?faces-redirect=true"; // Default to index if no valid referer
        }
    }

    public String returnToSamePage() {
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

    public boolean getHideDocumentList() {
        return hideDocumentList;
    }

    public void setHideDocumentList(boolean hideDocumentList) {
        this.hideDocumentList = hideDocumentList;
    }
}
