package ua.ivan909020.bot.repositories.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ua.ivan909020.bot.models.entities.Category;
import ua.ivan909020.bot.models.entities.Client;
import ua.ivan909020.bot.models.entities.Message;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.entities.OrderItem;
import ua.ivan909020.bot.models.entities.OrderStatus;
import ua.ivan909020.bot.models.entities.Product;

public final class HibernateSessionFactory {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateSessionFactory() {
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        new EnvironmentPropertiesPopulator().populate(configuration.getProperties());
        addAnnotatedClasses(configuration);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    private static void addAnnotatedClasses(Configuration configuration) {
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(OrderItem.class);
        configuration.addAnnotatedClass(OrderStatus.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Message.class);
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

}
