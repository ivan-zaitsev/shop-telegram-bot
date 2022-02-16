package ua.ivan909020.bot.repositories.database;

import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.repositories.OrderRepository;

import static ua.ivan909020.bot.repositories.hibernate.HibernateTransactionFactory.inTransactionVoid;

public class OrderRepositoryDefault implements OrderRepository {

    @Override
    public void save(Order order) {
        inTransactionVoid(session -> session.save(order));
    }

}
