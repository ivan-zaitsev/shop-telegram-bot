package ua.ivanzaitsev.bot.services;

import ua.ivanzaitsev.bot.models.entities.Message;

public interface MessageService {

    Message findByName(String messageName);

}
