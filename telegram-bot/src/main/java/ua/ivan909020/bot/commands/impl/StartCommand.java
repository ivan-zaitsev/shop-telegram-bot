package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;
import ua.ivan909020.bot.utils.KeyboardUtils;

import java.util.ArrayList;

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
        createClient(chatId);
        telegramService.sendMessage(new MessageSend(chatId, "Chatbot online shop", createGeneralMenuKeyboard()));
    }

    private void createClient(Long chatId) {
        Client client = new Client();
        client.setChatId(chatId);
        clientService.save(client);
    }

    public static ReplyKeyboardMarkup createGeneralMenuKeyboard() {
        return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{
                add("\uD83C\uDF7D Menu");
                add("\uD83D\uDECD Cart");
            }});
        }});
    }

}
