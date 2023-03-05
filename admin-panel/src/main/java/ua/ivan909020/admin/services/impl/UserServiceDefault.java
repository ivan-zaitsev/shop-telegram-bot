package ua.ivan909020.admin.services.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.ivan909020.admin.exceptions.ValidationException;
import ua.ivan909020.admin.models.entities.User;
import ua.ivan909020.admin.repositories.UserRepository;
import ua.ivan909020.admin.services.UserService;

@Service
public class UserServiceDefault implements UserService {

    private final UserRepository repository;
    
    private final PasswordEncoder passwordEncoder;

    public UserServiceDefault(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
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

        User receivedUser = findById(user.getId());
        receivedUser.setName(user.getName());
        receivedUser.setUsername(user.getUsername());
        if (!user.getPassword().isEmpty()) {
            receivedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        receivedUser.setRole(user.getRole());
        return repository.save(receivedUser);
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
