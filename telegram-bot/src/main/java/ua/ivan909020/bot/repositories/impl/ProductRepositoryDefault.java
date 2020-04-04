package ua.ivan909020.bot.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.ivan909020.bot.domain.entities.Product;
import ua.ivan909020.bot.repositories.ProductRepository;

import java.util.List;

public class ProductRepositoryDefault implements ProductRepository {

    private final SessionFactory sessionFactory = HibernateFactory.getSessionFactory();

    @Override
    public Product findById(Integer productId) {
        Session session = sessionFactory.openSession();
        Product product = session.get(Product.class, productId);
        session.close();
        return product;
    }

    @Override
    public List<Product> findAllByCategoryName(String categoryName, int offset, int size) {
        Session session = sessionFactory.openSession();
        List<Product> products = session.createQuery("from Product where category.name = :categoryName", Product.class)
                .setParameter("categoryName", categoryName)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
        session.close();
        return products;
    }

}
