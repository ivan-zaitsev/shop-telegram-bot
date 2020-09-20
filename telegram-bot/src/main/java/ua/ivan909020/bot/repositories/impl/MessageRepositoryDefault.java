package ua.ivan909020.bot.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.ivan909020.bot.domain.entities.Message;
import ua.ivan909020.bot.repositories.MessageRepository;

public class MessageRepositoryDefault implements MessageRepository {

    private final SessionFactory sessionFactory = HibernateFactory.getSessionFactory();

    @Override
    public Message findByName(String messageName) {
        Session session = sessionFactory.openSession();
        Message message = session.createQuery("from Message where name = :name", Message.class)
                .setParameter("name", messageName)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return message;
    }

}
