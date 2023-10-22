package ua.ivanzaitsev.bot.repositories;

import java.util.List;

import ua.ivanzaitsev.bot.models.entities.Category;

public interface CategoryRepository {

    List<Category> findAll();

}
