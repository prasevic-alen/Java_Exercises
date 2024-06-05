package com.uni.lu.repository;

import com.uni.lu.entity.Document;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
        em.persist(document);
    }

    @Transactional // Always wrap update/delete in a transaction
    public void updateDocument(Document document) {
        em.merge(document); // Update the existing document
    }

    @Transactional
    public void deleteDocument(Document document) {
        em.remove(em.merge(document)); // First merge to get managed, then remove
    }
}
