package ua.ivanzaitsev.bot.repositories.database;

import static ua.ivanzaitsev.bot.repositories.hibernate.HibernateTransactionFactory.inTransaction;

import ua.ivanzaitsev.bot.models.entities.Message;
import ua.ivanzaitsev.bot.repositories.MessageRepository;

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
