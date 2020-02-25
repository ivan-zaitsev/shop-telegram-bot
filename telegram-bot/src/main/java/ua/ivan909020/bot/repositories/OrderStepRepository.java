package ua.ivan909020.bot.repositories;

import java.util.Map;

import ua.ivan909020.bot.commands.Command;

public interface OrderStepRepository {

    void setOrderStepNumber(Long chatId, Integer orderStep);

    Integer findOrderStepNumberByChatId(Long chatId);

    Map<Integer, Command> getOrderSteps();

}
