package br.com.fuctura;

import br.com.fuctura.dao.BookDAO;
import br.com.fuctura.dao.CategoryDAO;
import br.com.fuctura.entity.Book;
import br.com.fuctura.entity.Category;

import java.util.List;


public class Application {
    public static void main(String[] args) {
        Book mybook = new Book();
        Category category = new Category();
//
//        mybook.setTitle("My Book6");
//        mybook.setAuthor("Autor6");
//        mybook.setSynopsis("My Booo Synopsis");
//        mybook.setIsbn("1238687878");
//        mybook.setReleaseYear(java.sql.Date.valueOf("2020-01-05"));
//
         BookDAO dao = new BookDAO();
         dao.existsById(2L);

         System.out.println(dao.findById(2L));

         dao.deleteById(2L);
//
        List<Book> allBooks = dao.findAll();
        System.out.println("==== Lista de todos os livros ====");
        for (Book book : allBooks) {
            System.out.println(book);
        }
//
        category.setName("Category 1");
        category.setDescription("Description 1");

        CategoryDAO categoryDao = new CategoryDAO();
//        categoryDao.save(category);
//
//        category.setName("Category 2");
//        category.setDescription("Description 2");
//
//        categoryDao.save(category);
//
        List<Category> allCategories = categoryDao.findAll();
        System.out.println("==== Lista de todos as categorias ====");
        for (Category categories : allCategories) {
            System.out.println(categories);
        }



        dao.close();



    }
}