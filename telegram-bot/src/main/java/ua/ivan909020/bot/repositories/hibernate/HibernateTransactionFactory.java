package ua.ivan909020.bot.repositories.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public final class HibernateTransactionFactory {

    private static final SessionFactory SESSION_FACTORY = HibernateSessionFactory.getSessionFactory();

    private HibernateTransactionFactory() {
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
