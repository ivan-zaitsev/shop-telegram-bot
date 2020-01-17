package ua.ivan909020.admin.services;

import ua.ivan909020.admin.domain.Category;

public interface CategoryService extends CrudService<Category> {

    Category save(Category category);

    Category update(Category category);

    void deleteById(Integer id);

}
