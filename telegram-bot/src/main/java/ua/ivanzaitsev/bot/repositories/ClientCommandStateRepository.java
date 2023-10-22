package ua.ivanzaitsev.bot.repositories;

import ua.ivanzaitsev.bot.models.domain.Command;

public interface ClientCommandStateRepository {

    void pushByChatId(Long chatId, Command command);

    Command popByChatId(Long chatId);

    void deleteAllByChatId(Long chatId);

}
