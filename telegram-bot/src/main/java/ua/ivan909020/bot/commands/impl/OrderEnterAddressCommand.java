package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.domain.entities.Order;
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

public class OrderEnterAddressCommand implements Command {

    private static final OrderEnterAddressCommand INSTANCE = new OrderEnterAddressCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

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
        Order order = orderStepService.findCachedOrderByChatId(chatId);
        if (order != null && order.getClient() != null && order.getClient().getAddress() != null) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current address: " + order.getClient().getAddress(), createKeyboard(true)));
        }
    }

    private ReplyKeyboardMarkup createKeyboard(boolean skipStep) {
        return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
            if (skipStep) {
                add(new KeyboardRow() {{
                    add(new KeyboardButton(Commands.ORDER_NEXT_STEP_COMMAND));
                }});
            }
            add(new KeyboardRow() {{
                add(new KeyboardButton(Commands.ORDER_CANCEL_COMMAND));
                add(new KeyboardButton(Commands.ORDER_PREVIOUS_STEP_COMMAND));
            }});
        }});
    }

    public void doEnterAddress(Long chatId, String address) {
        Matcher matcher = ADDRESS_PATTERN.matcher(address);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "You entered the incorrect address, try again."));
            return;
        }
        Order order = orderStepService.findCachedOrderByChatId(chatId);
        if (order != null && order.getClient() != null) {
            order.getClient().setAddress(address);
            orderStepService.updateCachedOrder(chatId, order);
        }
        orderStepService.nextOrderStep(chatId);
    }

}
