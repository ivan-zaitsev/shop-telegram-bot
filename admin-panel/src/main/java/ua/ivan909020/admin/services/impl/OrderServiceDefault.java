package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan909020.admin.domain.Order;
import ua.ivan909020.admin.exceptions.ValidationException;
import ua.ivan909020.admin.repositories.OrderRepository;
import ua.ivan909020.admin.services.OrderService;

import java.util.List;

@Service
public class OrderServiceDefault implements OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderServiceDefault(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Order should not be NULL");
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order should not be NULL");
        }
        if (order.getId() != null) {
            throw new ValidationException("Id of Order should be NULL");
        }
        return repository.save(order);
    }

    @Override
    public Order update(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order should not be NULL");
        }
        if (order.getId() == null) {
            throw new ValidationException("Id of Order should not be NULL");
        }
        return repository.save(order);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Order should not be NULL");
        }
        repository.deleteById(id);
    }

}
