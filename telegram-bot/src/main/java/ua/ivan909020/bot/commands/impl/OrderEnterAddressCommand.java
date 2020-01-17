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

public class OrderEnterAddressCommand implements Command {

    private static final OrderEnterAddressCommand INSTANCE = new OrderEnterAddressCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("[a-zA-Z]");

    private OrderEnterAddressCommand() {
    }

    public static OrderEnterAddressCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        clientService.setActionForChatId(chatId, "order=enter-client-address");
        telegramService.sendMessage(new MessageSend(chatId, "Enter your address", createKeyboard(false)));
        sendCurrentAddress(chatId);
    }

    private void sendCurrentAddress(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client != null && client.getAddress() != null && !client.getAddress().isEmpty()) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current address: " + client.getAddress(), createKeyboard(true)));
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

    public void doEnterAddress(Long chatId, String address) {
        Matcher matcher = ADDRESS_PATTERN.matcher(address);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "Enter your address!"));
            return;
        }
        Client client = clientService.findByChatId(chatId);
        if (client != null) {
            client.setAddress(address);
            clientService.update(client);
        }
    }

}
