package ua.ivan909020.bot.commands.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.MessageService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.MessageServiceCached;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

public class StartCommand implements Command<Long> {

    private static final StartCommand INSTANCE = new StartCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final MessageService messageService = MessageServiceCached.getInstance();

    private StartCommand() {
    }

    public static StartCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client == null) {
            saveClient(chatId);
        } else if (!client.isActive()) {
            activateClient(client);
        }
        sendStartMessage(chatId);
    }

    private void saveClient(Long chatId) {
        Client client = new Client();
        client.setChatId(chatId);
        client.setActive(true);
        clientService.save(client);
    }

    private void activateClient(Client client) {
        client.setActive(true);
        clientService.update(client);
    }

    private void sendStartMessage(Long chatId) {
        String message = messageService.findByName("START_MESSAGE").buildText();
        telegramService.sendMessage(new MessageSend(chatId, message, Commands.createGeneralMenuKeyboard()));
    }

}
