package ua.ivan909020.bot.repositories.database;

import ua.ivan909020.bot.models.entities.Message;
import ua.ivan909020.bot.repositories.MessageRepository;

import static ua.ivan909020.bot.repositories.hibernate.HibernateTransactionFactory.inTransaction;

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
