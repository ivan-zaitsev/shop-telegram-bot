package ua.ivan909020.bot.services;

import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.domain.entities.OrderItem;
import ua.ivan909020.bot.domain.models.CartItem;

import java.util.List;

public interface OrderService {

    void save(Order order);

    List<OrderItem> from(List<CartItem> cartItems);

}
