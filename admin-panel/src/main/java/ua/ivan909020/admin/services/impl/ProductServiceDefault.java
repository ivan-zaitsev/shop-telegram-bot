package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan909020.admin.domain.Product;
import ua.ivan909020.admin.exceptions.ValidationException;
import ua.ivan909020.admin.repositories.ProductRepository;
import ua.ivan909020.admin.services.ProductService;

import java.util.List;

@Service
public class ProductServiceDefault implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceDefault(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Product should not be NULL");
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product should not be NULL");
        }
        if (product.getId() != null) {
            throw new ValidationException("Id of Product should be NULL");
        }
        return repository.save(product);
    }

    @Override
    public Product update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product should not be NULL");
        }
        if (product.getId() == null) {
            throw new ValidationException("Id of Product should not be NULL");
        }
        return repository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Product should not be NULL");
        }
        repository.deleteById(id);
    }

}
