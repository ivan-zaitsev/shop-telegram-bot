package ua.ivan909020.bot.services.impl;

import java.util.ArrayList;
import java.util.List;

import ua.ivan909020.bot.models.domain.CartItem;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.entities.OrderItem;
import ua.ivan909020.bot.repositories.OrderRepository;
import ua.ivan909020.bot.repositories.database.OrderRepositoryDefault;
import ua.ivan909020.bot.services.OrderService;

public class OrderServiceDefault implements OrderService {

    private static final OrderService INSTANCE = new OrderServiceDefault();

    private final OrderRepository repository = new OrderRepositoryDefault();

    private OrderServiceDefault() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order should not be NULL");
        }

        repository.save(order);
    }

    @Override
    public List<OrderItem> fromCartItems(List<CartItem> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setProductPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        return orderItems;
    }

}
