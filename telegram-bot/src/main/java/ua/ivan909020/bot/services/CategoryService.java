package ua.ivan909020.bot.services;

import ua.ivan909020.bot.domain.entities.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Integer categoryId);

    List<Category> findAll();

}
