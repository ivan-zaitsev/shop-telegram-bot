package ua.ivanzaitsev.bot.repositories;

import ua.ivanzaitsev.bot.models.domain.ClientAction;

public interface ClientActionRepository {

    ClientAction findByChatId(Long chatId);

    void updateByChatId(Long chatId, ClientAction clientAction);

    void deleteByChatId(Long chatId);

}
