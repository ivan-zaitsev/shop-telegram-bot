package ua.ivan909020.bot.repositories.database;

import ua.ivan909020.bot.models.entities.Product;
import ua.ivan909020.bot.repositories.ProductRepository;

import java.util.List;

import static ua.ivan909020.bot.repositories.hibernate.HibernateFactory.inTransaction;

public class ProductRepositoryDefault implements ProductRepository {

    @Override
    public Product findById(Integer productId) {
        return inTransaction(session -> session.get(Product.class, productId));
    }

    @Override
    public List<Product> findAllByCategoryName(String categoryName, int offset, int size) {
        String query = "from Product where category.name = :categoryName";

        return inTransaction(session ->
                session.createQuery(query, Product.class)
                        .setParameter("categoryName", categoryName)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList()
        );
    }

}
