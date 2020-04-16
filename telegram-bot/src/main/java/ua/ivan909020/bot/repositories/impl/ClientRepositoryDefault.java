package ua.ivan909020.bot.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.models.ClientAction;
import ua.ivan909020.bot.repositories.ClientRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ClientRepositoryDefault implements ClientRepository {

    private final SessionFactory sessionFactory = HibernateFactory.getSessionFactory();

    private final Map<Long, ClientAction> clientsAction = new HashMap<>();

    @Override
    public String findActionByChatId(Long chatId) {
        ClientAction clientAction = clientsAction.get(chatId);
        if (clientAction != null && LocalDateTime.now().isAfter(clientAction.getCreatedTime().plusMinutes(5))) {
            clientAction = null;
        }
        return clientAction == null ? null : clientAction.getAction();
    }

    @Override
    public void setActionForChatId(Long chatId, String action) {
        clientsAction.put(chatId, new ClientAction(action, LocalDateTime.now()));
    }

    @Override
    public Client findByChatId(Long chatId) {
        Session session = sessionFactory.openSession();
        Client client = (Client) session.createQuery("from Client where chatId = :chatId")
                .setParameter("chatId", chatId).setMaxResults(1).uniqueResult();
        session.close();
        return client;
    }

    @Override
    public void save(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(client);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(client);
        transaction.commit();
        session.close();
    }

}
