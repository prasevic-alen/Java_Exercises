package com.uni.lu.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "abstract_text")
    private String abstractText;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "storage_location")
    private String storageLocation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "Document_Author",
      joinColumns = @JoinColumn(name = "document_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id")
    )

    private List<Author> authors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date date) {
        this.publicationDate = date;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Integer> getAuthorIds() {
        // Assuming 'authors' is a List<Author>
        return authors.stream().map(Author::getId).collect(Collectors.toList());
    }
}
