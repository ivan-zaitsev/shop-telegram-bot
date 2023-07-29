package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.domain.Command;

public interface ClientCommandStateRepository {

    void pushByChatId(Long chatId, Command command);

    Command popByChatId(Long chatId);

    void deleteAllByChatId(Long chatId);

}
