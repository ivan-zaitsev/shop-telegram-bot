package ua.ivan909020.bot.services;

import ua.ivan909020.bot.models.entities.Product;

import java.util.List;

public interface ProductService {

    Product findById(Integer productId);

    List<Product> findAllByCategoryName(String categoryName, int offset, int size);

}
