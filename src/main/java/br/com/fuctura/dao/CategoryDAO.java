package br.com.fuctura.dao;

import br.com.fuctura.entity.Category;

public class CategoryDAO extends GenericDAO<Category, Long> {
    public CategoryDAO() {
        super(Category.class);
    }

}
