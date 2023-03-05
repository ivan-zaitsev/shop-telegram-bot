package ua.ivan909020.bot.services;

import java.util.List;

import ua.ivan909020.bot.models.domain.CartItem;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.entities.OrderItem;

public interface OrderService {

    void save(Order order);

    List<OrderItem> fromCartItems(List<CartItem> cartItems);

}
