package ua.ivanzaitsev.admin.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ua.ivanzaitsev.admin.models.entities.Product;

public interface ProductService {

    Product findById(Integer id);

    List<Product> findAll();

    Product save(Product product, MultipartFile photo);

    Product update(Product product, MultipartFile photo);

    void deleteById(Integer id);

}
