package ua.ivan909020.admin.services;

import ua.ivan909020.admin.models.entities.Order;

import java.util.List;

public interface OrderService {

    Order findById(Integer id);

    List<Order> findAll();

    Order save(Order order);

    Order update(Order order);

    void deleteById(Integer id);

}
