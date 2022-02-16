package ua.ivan909020.bot.repositories.database;

import ua.ivan909020.bot.models.entities.Category;
import ua.ivan909020.bot.repositories.CategoryRepository;

import java.util.List;

import static ua.ivan909020.bot.repositories.hibernate.HibernateTransactionFactory.inTransaction;

public class CategoryRepositoryDefault implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        String query = "from Category";

        return inTransaction(session -> session.createQuery(query, Category.class).getResultList());
    }

}
