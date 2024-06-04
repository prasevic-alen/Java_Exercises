package com.uni.lu.repository;

import com.uni.lu.entity.Document;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DocumentRepository {

    @PersistenceContext
    private EntityManager em;

    @EJB // Inject AuthorRepository
    private AuthorRepository authorRepository;

    public List<Document> findAllDocuments() {
        return em.createQuery("SELECT d FROM Document d", Document.class).getResultList();
    }

    public List<Document> findDocumentsByPublicationYear(int year) {
        return em.createQuery("SELECT d FROM Document d WHERE YEAR(d.publicationDate) = :year", Document.class)
                .setParameter("year", year)
                .getResultList();
    }

    public Document findDocumentById(int documentId) {
        return em.createQuery("SELECT d FROM Document d JOIN FETCH d.authors WHERE d.id = :documentId", Document.class)
                .setParameter("documentId", documentId)
                .getSingleResult(); 
    }

    public void createDocument(Document document) {
        // Fetch authors by their IDs and set them in the document
        document.setAuthors(authorRepository.findAuthorsByIds(document.getAuthorIds())); // Assuming you have getAuthorIds() method in Document

        em.persist(document);
    }
}
