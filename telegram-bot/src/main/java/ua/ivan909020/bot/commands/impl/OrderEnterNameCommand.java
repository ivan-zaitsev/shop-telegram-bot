package ua.ivan909020.bot.commands.impl;

import com.mchange.v2.lang.StringUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.exceptions.OrderStepStateException;
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

public class OrderEnterNameCommand implements Command<Long> {

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
        Order order = orderStepService.findCachedOrderByChatId(chatId);
        if (order == null || order.getClient() == null) {
            throw new OrderStepStateException("Order step state error for client with chatId" + chatId);
        }
        if (StringUtils.nonWhitespaceString(order.getClient().getName())) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current name: " + order.getClient().getName(), createKeyboard(true)));
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
            }});
        }});
    }

    public void doEnterName(Long chatId, String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "You entered the incorrect name, try again."));
            return;
        }
        Order order = orderStepService.findCachedOrderByChatId(chatId);
        if (order == null || order.getClient() == null) {
            throw new OrderStepStateException("Order step state error for client with chatId" + chatId);
        }
        order.getClient().setName(name);
        orderStepService.updateCachedOrder(chatId, order);
        orderStepService.nextOrderStep(chatId);
    }

}
