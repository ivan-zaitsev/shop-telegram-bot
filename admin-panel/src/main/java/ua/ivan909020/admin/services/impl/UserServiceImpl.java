package ua.ivan909020.admin.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.ivan909020.admin.domain.User;
import ua.ivan909020.admin.exceptions.ValidationException;
import ua.ivan909020.admin.repositories.UserRepository;
import ua.ivan909020.admin.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of User should not be NULL");
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User should not be NULL");
        }
        if (user.getId() != null) {
            throw new ValidationException("Id of User should be NULL");
        }
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User should not be NULL");
        }
        if (user.getId() == null) {
            throw new ValidationException("Id of User should not be NULL");
        }
        return repository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of User should not be NULL");
        }
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username);
    }

}
