package ua.ivan909020.bot.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.repositories.OrderRepository;

public class OrderRepositoryDefault implements OrderRepository {

    private final SessionFactory sessionFactory = HibernateFactory.getSessionFactory();

    @Override
    public void save(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(order);

        transaction.commit();
        session.close();
    }

}
