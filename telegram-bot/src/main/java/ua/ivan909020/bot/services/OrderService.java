package ua.ivan909020.bot.services;

import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.entities.OrderItem;
import ua.ivan909020.bot.models.domain.CartItem;

import java.util.List;

public interface OrderService {

    void save(Order order);

    List<OrderItem> fromCartItems(List<CartItem> cartItems);

}
