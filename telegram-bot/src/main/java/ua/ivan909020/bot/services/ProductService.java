package ua.ivan909020.bot.services;

import java.util.List;

import ua.ivan909020.bot.models.entities.Product;

public interface ProductService {

    Product findById(Integer productId);

    List<Product> findAllByCategoryName(String categoryName, int offset, int size);

}
