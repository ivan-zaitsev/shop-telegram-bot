package ua.ivan909020.admin.services;

import ua.ivan909020.admin.domain.Order;

public interface OrderService extends CrudService<Order> {

    Order save(Order order);

    Order update(Order order);

    void deleteById(Integer id);

}
