package ua.ivan909020.bot.repositories.impl;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ua.ivan909020.bot.domain.entities.*;

class HibernateFactory {

    private static SessionFactory sessionFactory;

    static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(Category.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(OrderItem.class);
            configuration.addAnnotatedClass(OrderState.class);
            configuration.addAnnotatedClass(Product.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }

}
