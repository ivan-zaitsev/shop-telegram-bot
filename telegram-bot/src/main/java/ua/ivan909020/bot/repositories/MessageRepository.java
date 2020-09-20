package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.domain.entities.Message;

public interface MessageRepository {

    Message findByName(String messageName);

}
