package ua.ivan909020.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.ivan909020.admin.models.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
