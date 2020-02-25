package ua.ivan909020.bot.repositories.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.impl.OrderCreateCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterAddressCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterCityCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterNameCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterPhoneNumberCommand;
import ua.ivan909020.bot.repositories.OrderStepRepository;

import java.util.HashMap;
import java.util.Map;

public class OrderStepRepositoryDefault implements OrderStepRepository {

    private Map<Long, Integer> orderStepNumbers = new HashMap<>();
    private Map<Integer, Command> orderSteps = new HashMap<>();

    @Override
    public void setOrderStepNumber(Long chatId, Integer orderStepNumber) {
        orderStepNumbers.put(chatId, orderStepNumber);
    }

    @Override
    public Integer findOrderStepNumberByChatId(Long chatId) {
        return orderStepNumbers.getOrDefault(chatId, 0);
    }

    @Override
    public Map<Integer, Command> getOrderSteps() {
	if (orderSteps.isEmpty()) {
	    orderSteps.put(1, OrderEnterNameCommand.getInstance());
	    orderSteps.put(2, OrderEnterPhoneNumberCommand.getInstance());
	    orderSteps.put(3, OrderEnterCityCommand.getInstance());
	    orderSteps.put(4, OrderEnterAddressCommand.getInstance());
	    orderSteps.put(5, OrderCreateCommand.getInstance());
	}
	return orderSteps;
    }

}
