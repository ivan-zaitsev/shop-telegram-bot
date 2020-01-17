package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.domain.entities.Product;
import ua.ivan909020.bot.repositories.ProductRepository;
import ua.ivan909020.bot.repositories.impl.ProductRepositoryDefault;
import ua.ivan909020.bot.services.ProductService;

import java.util.List;

public class ProductServiceDefault implements ProductService {

    private static final ProductService INSTANCE = new ProductServiceDefault();

    private ProductRepository repository = new ProductRepositoryDefault();

    private ProductServiceDefault() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    @Override
    public Product findById(Integer productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Id of Product should not be NULL");
        }
        return repository.findById(productId);
    }

    @Override
    public List<Product> findAllByCategoryName(String categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Name of Category should not be NULL");
        }
        return repository.findAllByCategoryName(categoryName);
    }

}
