package ua.ivan909020.admin.services;

import java.util.List;

import ua.ivan909020.admin.models.entities.Order;

public interface OrderService {

    Order findById(Integer id);

    List<Order> findAll();

    Order save(Order order);

    Order update(Order order);

    void deleteById(Integer id);

}
