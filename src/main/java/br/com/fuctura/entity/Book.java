package br.com.fuctura.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String title;
    private String author;
    private String synopsis;

    @Column(name = "isbn")
    private String isbn;
    private Date releaseYear;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Book() {
    }

    public Book(Long id, String title, String author, String synopsis, String isbn, Date releaseYear, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.isbn = isbn;
        this.releaseYear = releaseYear;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', isbn='%s', year=%s}",
                id,
                title != null ? title : "null",
                author != null ? author : "null",
                isbn != null ? isbn : "null",
                releaseYear != null ? new java.text.SimpleDateFormat("yyyy").format(releaseYear) : "null"
        );
    }
}
