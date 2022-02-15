package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.domain.ClientAction;

public interface ClientActionRepository {

    ClientAction findActionByChatId(Long chatId);

    void updateActionByChatId(Long chatId, ClientAction clientAction);

    void deleteActionByChatId(Long chatId);

}
