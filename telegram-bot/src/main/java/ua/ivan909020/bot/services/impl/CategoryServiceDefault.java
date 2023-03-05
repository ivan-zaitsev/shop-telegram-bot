package ua.ivan909020.bot.services.impl;

import java.util.List;

import ua.ivan909020.bot.models.entities.Category;
import ua.ivan909020.bot.repositories.CategoryRepository;
import ua.ivan909020.bot.repositories.database.CategoryRepositoryDefault;
import ua.ivan909020.bot.services.CategoryService;

public class CategoryServiceDefault implements CategoryService {

    private static final CategoryService INSTANCE = new CategoryServiceDefault();

    private final CategoryRepository repository = new CategoryRepositoryDefault();

    private CategoryServiceDefault() {
    }

    public static CategoryService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

}
