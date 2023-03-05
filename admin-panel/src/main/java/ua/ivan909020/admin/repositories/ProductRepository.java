package ua.ivan909020.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.ivan909020.admin.models.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
