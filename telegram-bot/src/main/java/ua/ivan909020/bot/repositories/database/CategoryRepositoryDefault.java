package ua.ivan909020.bot.repositories.database;

import static ua.ivan909020.bot.repositories.hibernate.HibernateTransactionFactory.inTransaction;

import java.util.List;

import ua.ivan909020.bot.models.entities.Category;
import ua.ivan909020.bot.repositories.CategoryRepository;

public class CategoryRepositoryDefault implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        String query = "from Category";

        return inTransaction(session -> session.createQuery(query, Category.class).getResultList());
    }

}
