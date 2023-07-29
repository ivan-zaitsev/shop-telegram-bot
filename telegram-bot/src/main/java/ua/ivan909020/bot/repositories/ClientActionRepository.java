package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.domain.ClientAction;

public interface ClientActionRepository {

    ClientAction findByChatId(Long chatId);

    void updateByChatId(Long chatId, ClientAction clientAction);

    void deleteByChatId(Long chatId);

}
