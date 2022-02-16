package ua.ivan909020.admin.services;

import ua.ivan909020.admin.models.entities.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Integer id);

    List<Category> findAll();

    Category save(Category category);

    Category update(Category category);

    void deleteById(Integer id);

}
