package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.entities.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

}
