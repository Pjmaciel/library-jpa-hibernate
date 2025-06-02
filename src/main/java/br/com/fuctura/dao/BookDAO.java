package br.com.fuctura.dao;

import br.com.fuctura.entity.Book;

import java.util.Date;
import java.util.List;

public class BookDAO extends GenericDAO<Book, Long> {
    public BookDAO() {
        super(Book.class);
    }

    public List<Book> findByAuthor(String author) {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    public List<Book> findByTitle(String titlePattern) {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.title LIKE :title", Book.class)
                .setParameter("title", "%" + titlePattern + "%")
                .getResultList();
    }

    public List<Book> findByRelease(Date releaseYear) {
        return entityManager
                .createQuery("SELECT b FROM Book b WHERE b.release > :releaseDate", Book.class)
                .setParameter("releaseDate", releaseYear)
                .getResultList();
    }

}
