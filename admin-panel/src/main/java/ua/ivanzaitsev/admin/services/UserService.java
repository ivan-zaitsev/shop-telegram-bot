package ua.ivanzaitsev.admin.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ua.ivanzaitsev.admin.models.entities.User;

public interface UserService extends UserDetailsService {

    User findById(Integer id);

    List<User> findAll();

    User save(User user);

    User update(User user);

    void deleteById(Integer id);

}
