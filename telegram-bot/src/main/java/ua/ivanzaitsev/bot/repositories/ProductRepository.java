package ua.ivanzaitsev.bot.repositories;

import java.util.List;

import ua.ivanzaitsev.bot.models.entities.Product;

public interface ProductRepository {

    Product findById(Integer productId);

    List<Product> findAllByCategoryName(String categoryName, int offset, int size);

}
