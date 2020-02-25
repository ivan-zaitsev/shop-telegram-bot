package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;
import ua.ivan909020.bot.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderEnterNameCommand implements Command {

    private static final OrderEnterNameCommand INSTANCE = new OrderEnterNameCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]");

    private OrderEnterNameCommand() {
    }

    public static OrderEnterNameCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        clientService.setActionForChatId(chatId, "order=enter-client-name");
        telegramService.sendMessage(new MessageSend(chatId, "Enter your name", createKeyboard(false)));
        sendCurrentName(chatId);
    }

    private void sendCurrentName(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client != null && client.getName() != null && !client.getName().isEmpty()) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current name: " + client.getName(), createKeyboard(true)));
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
            }});
        }});
    }

    public void doEnterName(Long chatId, String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "Enter your name!"));
            return;
        }
        Client client = clientService.findByChatId(chatId);
        if (client != null) {
            client.setName(name);
            clientService.update(client);
        }
        orderStepService.nextOrderStep(chatId);
    }

}
