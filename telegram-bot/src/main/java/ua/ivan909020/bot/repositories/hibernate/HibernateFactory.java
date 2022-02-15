package ua.ivan909020.bot.repositories.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import ua.ivan909020.bot.models.entities.Category;
import ua.ivan909020.bot.models.entities.Client;
import ua.ivan909020.bot.models.entities.Message;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.entities.OrderItem;
import ua.ivan909020.bot.models.entities.OrderStatus;
import ua.ivan909020.bot.models.entities.Product;

public final class HibernateFactory {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateFactory() {
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
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

    public static void inTransactionVoid(TransactionVoidFunction function) {
        inTransaction(session -> {
            function.accept(session);
            return null;
        });
    }

    public static <T> T inTransaction(TransactionFunction<T> function) {
        T result;

        Session session = SESSION_FACTORY.getCurrentSession();
        Transaction transaction = session.getTransaction();

        boolean isTransactionNotExists = TransactionStatus.NOT_ACTIVE.equals(transaction.getStatus());
        if (isTransactionNotExists) {
            transaction = session.beginTransaction();
        }

        try {
            result = function.apply(session);
            if (isTransactionNotExists) {
                transaction.commit();
            }
        } catch (Exception e) {
            if (isTransactionNotExists) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (isTransactionNotExists) {
                session.close();
            }
        }

        return result;
    }

}
