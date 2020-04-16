package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.domain.entities.Category;
import ua.ivan909020.bot.repositories.CategoryRepository;
import ua.ivan909020.bot.repositories.impl.CategoryRepositoryDefault;
import ua.ivan909020.bot.services.CategoryService;

import java.util.List;

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
