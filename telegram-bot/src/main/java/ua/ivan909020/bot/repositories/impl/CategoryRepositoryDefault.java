package ua.ivan909020.bot.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.ivan909020.bot.domain.entities.Category;
import ua.ivan909020.bot.repositories.CategoryRepository;

import java.util.List;

public class CategoryRepositoryDefault implements CategoryRepository {

    private final SessionFactory sessionFactory = HibernateFactory.getSessionFactory();

    @Override
    public Category findById(Integer categoryId) {
        Session session = sessionFactory.openSession();
        Category category = session.get(Category.class, categoryId);
        session.close();
        return category;
    }

    @Override
    public List<Category> findAll() {
        Session session = sessionFactory.openSession();
        List<Category> categories = session.createQuery("from Category", Category.class).getResultList();
        session.close();
        return categories;
    }

}
