package ua.ivan909020.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ivan909020.admin.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
