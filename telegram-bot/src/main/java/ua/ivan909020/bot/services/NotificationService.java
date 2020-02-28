package ua.ivan909020.bot.services;

import ua.ivan909020.bot.domain.entities.Client;

public interface NotificationService {
    public void notifyAdmin(Client client);
}
