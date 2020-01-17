package ua.ivan909020.admin.services;

import java.util.List;

public interface CrudService<T> {

    T findById(Integer id);

    List<T> findAll();

}
