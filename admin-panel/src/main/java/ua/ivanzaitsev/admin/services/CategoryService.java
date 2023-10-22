package ua.ivanzaitsev.admin.services;

import java.util.List;

import ua.ivanzaitsev.admin.models.entities.Category;

public interface CategoryService {

    Category findById(Integer id);

    List<Category> findAll();

    Category save(Category category);

    Category update(Category category);

    void deleteById(Integer id);

}
