package ua.ivan909020.admin.services;

import ua.ivan909020.admin.models.entities.Product;

import java.util.List;

public interface ProductService {

    Product findById(Integer id);

    List<Product> findAll();

    Product save(Product product);

    Product update(Product product);

    void deleteById(Integer id);

}
