package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.entities.Product;

import java.util.List;

public interface ProductRepository {

    Product findById(Integer productId);

    List<Product> findAllByCategoryName(String categoryName, int offset, int size);

}
