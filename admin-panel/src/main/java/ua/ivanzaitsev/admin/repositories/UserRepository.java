package ua.ivanzaitsev.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.ivanzaitsev.admin.models.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
