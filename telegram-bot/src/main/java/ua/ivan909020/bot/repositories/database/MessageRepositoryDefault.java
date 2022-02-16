package ua.ivan909020.bot.repositories.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.ivan909020.bot.models.entities.Message;
import ua.ivan909020.bot.repositories.MessageRepository;
import ua.ivan909020.bot.repositories.hibernate.HibernateFactory;

import static ua.ivan909020.bot.repositories.hibernate.HibernateFactory.inTransaction;

public class MessageRepositoryDefault implements MessageRepository {

    @Override
    public Message findByName(String messageName) {
        String query = "from Message where name = :name";

        return inTransaction(session ->
                session.createQuery(query, Message.class)
                        .setParameter("name", messageName)
                        .setMaxResults(1)
                        .uniqueResult()
        );
    }

}
