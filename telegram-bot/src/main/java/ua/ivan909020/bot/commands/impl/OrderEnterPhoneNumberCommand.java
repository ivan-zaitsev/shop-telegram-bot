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

public class OrderEnterPhoneNumberCommand implements Command {

    private static final OrderEnterPhoneNumberCommand INSTANCE = new OrderEnterPhoneNumberCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();

    private static final Pattern PHONE_NUMBER_PATTERN =
            Pattern.compile("^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$");

    private OrderEnterPhoneNumberCommand() {
    }

    public static OrderEnterPhoneNumberCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        clientService.setActionForChatId(chatId, "order=enter-client-phone-number");
        telegramService.sendMessage(new MessageSend(chatId,
                "Enter your phone number or press button", createKeyboard(false)));
        sendCurrentPhoneNumber(chatId);
    }

    private void sendCurrentPhoneNumber(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client != null && client.getPhoneNumber() != null && !client.getPhoneNumber().isEmpty()) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current phone number: " + client.getPhoneNumber(), createKeyboard(true)));
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
                add(new KeyboardButton("\uD83D\uDCF1 Send phone number").setRequestContact(true));
            }});
            add(new KeyboardRow() {{
                add(new KeyboardButton("\u274C Cancel order"));
                add(new KeyboardButton("\u25C0 Back"));
            }});
        }});
    }

    public void doEnterPhoneNumber(Long chatId, String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "Enter your phone number or press button!"));
            return;
        }
        Client client = clientService.findByChatId(chatId);
        if (client != null) {
            client.setPhoneNumber(phoneNumber);
            clientService.update(client);
        }
    }

}
