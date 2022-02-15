package ua.ivan909020.admin.services.impl;

import org.springframework.stereotype.Service;
import ua.ivan909020.admin.exceptions.ValidationException;
import ua.ivan909020.admin.models.entities.Category;
import ua.ivan909020.admin.repositories.CategoryRepository;
import ua.ivan909020.admin.services.CategoryService;

import java.util.List;

@Service
public class CategoryServiceDefault implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceDefault(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Category should not be NULL");
        }

        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category should not be NULL");
        }
        if (category.getId() != null) {
            throw new ValidationException("Id of Category should be NULL");
        }

        return repository.save(category);
    }

    @Override
    public Category update(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category should not be NULL");
        }
        if (category.getId() == null) {
            throw new ValidationException("Id of Category should not be NULL");
        }

        return repository.save(category);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Category should not be NULL");
        }

        repository.deleteById(id);
    }

}
