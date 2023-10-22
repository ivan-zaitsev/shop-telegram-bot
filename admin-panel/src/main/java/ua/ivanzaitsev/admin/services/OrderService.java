package ua.ivanzaitsev.admin.services;

import java.util.List;

import ua.ivanzaitsev.admin.models.entities.Order;

public interface OrderService {

    Order findById(Integer id);

    List<Order> findAll();

    Order save(Order order);

    Order update(Order order);

    void deleteById(Integer id);

}
