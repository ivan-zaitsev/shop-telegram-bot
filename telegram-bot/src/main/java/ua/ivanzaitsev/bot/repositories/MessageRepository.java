package ua.ivanzaitsev.bot.repositories;

import ua.ivanzaitsev.bot.models.entities.Message;

public interface MessageRepository {

    Message findByName(String messageName);

}
