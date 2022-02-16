package ua.ivan909020.bot.services;

import ua.ivan909020.bot.models.domain.ClientAction;

public interface ClientActionService {

    ClientAction findActionByChatId(Long chatId);

    void updateActionByChatId(Long chatId, ClientAction clientAction);

    void deleteActionByChatId(Long chatId);
}
