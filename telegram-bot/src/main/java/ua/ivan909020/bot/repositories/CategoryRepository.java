package ua.ivan909020.bot.repositories;

import java.util.List;

import ua.ivan909020.bot.models.entities.Category;

public interface CategoryRepository {

    List<Category> findAll();

}
