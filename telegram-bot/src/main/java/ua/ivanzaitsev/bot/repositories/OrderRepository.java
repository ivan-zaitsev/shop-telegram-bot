package ua.ivanzaitsev.bot.repositories;

import ua.ivanzaitsev.bot.models.entities.Order;

public interface OrderRepository {

    void save(Order order);

}
