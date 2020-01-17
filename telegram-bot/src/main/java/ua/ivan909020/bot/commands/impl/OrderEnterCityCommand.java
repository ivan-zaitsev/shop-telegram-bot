package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderEnterCityCommand implements Command {

    private static final OrderEnterCityCommand INSTANCE = new OrderEnterCityCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();

    private static final Pattern CITY_PATTERN = Pattern.compile("[a-zA-Z]");

    private OrderEnterCityCommand() {
    }

    public static OrderEnterCityCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        clientService.setActionForChatId(chatId, "order=enter-client-city");
        telegramService.sendMessage(new MessageSend(chatId, "Enter your city", createKeyboard(false)));
        sendCurrentCity(chatId);
    }

    private void sendCurrentCity(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client != null && client.getCity() != null && !client.getCity().isEmpty()) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current city: " + client.getCity(), createKeyboard(true)));
        }
    }

    private ReplyKeyboardMarkup createKeyboard(boolean skipStep) {
        return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
            if (skipStep) {
                add(new KeyboardRow() {{
                    add(new KeyboardButton("\u2714\uFE0F Correct"));
                }});
            }
            add(new KeyboardRow() {{
                add(new KeyboardButton("\u274C Cancel order"));
                add(new KeyboardButton("\u25C0 Back"));
            }});
        }});
    }

    public void doEnterCity(Long chatId, String city) {
        Matcher matcher = CITY_PATTERN.matcher(city);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "Enter your city!"));
            return;
        }
        Client client = clientService.findByChatId(chatId);
        if (client != null) {
            client.setCity(city);
            clientService.update(client);
        }
    }

}
