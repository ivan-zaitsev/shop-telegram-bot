package ua.ivan909020.bot.commands.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

public class StartCommand implements Command {

    private static final StartCommand INSTANCE = new StartCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();

    private StartCommand() {
    }

    public static StartCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        if (clientService.findByChatId(chatId) == null) {
            saveClient(chatId);
        }
        telegramService.sendMessage(new MessageSend(chatId, "Online shop :)", Commands.createGeneralMenuKeyboard()));
    }

    private void saveClient(Long chatId) {
        Client client = new Client();
        client.setChatId(chatId);
        clientService.save(client);
    }

}
