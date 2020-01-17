package ua.ivan909020.admin.services;

import ua.ivan909020.admin.domain.Product;

public interface ProductService extends CrudService<Product> {

    Product save(Product product);

    Product update(Product product);

    void deleteById(Integer id);

}
