package ua.ivan909020.admin.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.ivan909020.admin.domain.User;

public interface UserService extends CrudService<User>, UserDetailsService {

    User save(User user);

    User update(User user);

    void deleteById(Integer id);

}
