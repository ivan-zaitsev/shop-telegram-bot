package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.commands.Command;

import java.util.Map;

public interface OrderStepRepository {

    void setOrderStepNumber(Long chatId, Integer orderStep);

    Integer findOrderStepNumberByChatId(Long chatId);

    Map<Integer, Command> getOrderSteps();

}
