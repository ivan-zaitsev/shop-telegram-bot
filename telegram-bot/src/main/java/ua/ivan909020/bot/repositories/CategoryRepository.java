package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.domain.entities.Category;

import java.util.List;

public interface CategoryRepository {

    Category findById(Integer categoryId);

    List<Category> findAll();

}
