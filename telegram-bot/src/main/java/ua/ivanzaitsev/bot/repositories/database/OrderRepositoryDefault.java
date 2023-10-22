package ua.ivanzaitsev.bot.repositories.database;

import static ua.ivanzaitsev.bot.repositories.hibernate.HibernateTransactionFactory.inTransactionVoid;

import ua.ivanzaitsev.bot.models.entities.Order;
import ua.ivanzaitsev.bot.repositories.OrderRepository;

public class OrderRepositoryDefault implements OrderRepository {

    @Override
    public void save(Order order) {
        inTransactionVoid(session -> session.persist(order));
    }

}
