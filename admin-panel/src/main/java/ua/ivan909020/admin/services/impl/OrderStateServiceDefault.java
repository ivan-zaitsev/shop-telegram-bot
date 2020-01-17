package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan909020.admin.domain.OrderState;
import ua.ivan909020.admin.repositories.OrderStateRepository;
import ua.ivan909020.admin.services.OrderStateService;

import java.util.List;

@Service
public class OrderStateServiceDefault implements OrderStateService {

    private final OrderStateRepository repository;

    @Autowired
    public OrderStateServiceDefault(OrderStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderState> findAll() {
        return repository.findAll();
    }

}
