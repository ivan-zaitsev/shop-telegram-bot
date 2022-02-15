package ua.ivan909020.bot.services;

import ua.ivan909020.bot.models.entities.Message;

public interface MessageService {

    Message findByName(String messageName);

}
