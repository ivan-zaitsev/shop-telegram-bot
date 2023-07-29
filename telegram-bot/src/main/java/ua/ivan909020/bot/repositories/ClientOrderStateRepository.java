package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.domain.ClientOrder;

public interface ClientOrderStateRepository {

    ClientOrder findByChatId(Long chatId);

    void updateByChatId(Long chatId, ClientOrder clientOrder);

    void deleteByChatId(Long chatId);

}
