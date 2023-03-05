package ua.ivan909020.bot.repositories.database;

import static ua.ivan909020.bot.repositories.hibernate.HibernateTransactionFactory.inTransaction;
import static ua.ivan909020.bot.repositories.hibernate.HibernateTransactionFactory.inTransactionVoid;

import ua.ivan909020.bot.models.entities.Client;
import ua.ivan909020.bot.repositories.ClientRepository;

public class ClientRepositoryDefault implements ClientRepository {

    @Override
    public Client findByChatId(Long chatId) {
        String query = "from Client where chatId = :chatId";

        return inTransaction(session ->
                session.createQuery(query, Client.class)
                        .setParameter("chatId", chatId)
                        .setMaxResults(1)
                        .uniqueResult()
        );
    }

    @Override
    public void save(Client client) {
        inTransactionVoid(session -> session.persist(client));
    }

    @Override
    public void update(Client client) {
        inTransactionVoid(session -> session.merge(client));
    }

}
