package ua.ivan909020.bot.repositories;

public interface OrderStepRepository {

    void setOrderStepNumber(Long chatId, Integer orderStep);

    Integer findOrderStepNumberByChatId(Long chatId);

}
