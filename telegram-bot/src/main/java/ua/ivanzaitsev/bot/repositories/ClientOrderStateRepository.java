package ua.ivanzaitsev.bot.repositories;

import ua.ivanzaitsev.bot.models.domain.ClientOrder;

public interface ClientOrderStateRepository {

    ClientOrder findByChatId(Long chatId);

    void updateByChatId(Long chatId, ClientOrder clientOrder);

    void deleteByChatId(Long chatId);

}
