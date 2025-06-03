package br.com.fuctura.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "release_year")
    private LocalDate releaseYear;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Book(String title, String author, String synopsis, String isbn, LocalDate releaseYear, Category category) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.isbn = isbn;
        this.releaseYear = releaseYear;
        this.category = category;
    }

    public boolean isValid() {
        return title != null && !title.trim().isEmpty()
                && author != null && !author.trim().isEmpty()
                && isbn != null && !isbn.trim().isEmpty()
                && releaseYear != null
                && category != null;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }

    // Sobrescrever toString do Lombok
    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', isbn='%s', year=%s}",
                id,
                title != null ? title : "null",
                author != null ? author : "null",
                isbn != null ? isbn : "null",
                releaseYear != null ? releaseYear.format(DateTimeFormatter.ofPattern("yyyy")) : "null"
        );
    }
}