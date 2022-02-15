package ua.ivan909020.bot.commands.impl.order;

import com.mchange.v2.lang.StringUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.domain.MessageSend;
import ua.ivan909020.bot.services.ClientActionService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientActionServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton.builder;

public class OrderEnterNameCommand implements CommandSequence<Long> {

    private static final OrderEnterNameCommand INSTANCE = new OrderEnterNameCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]");

    private OrderEnterNameCommand() {
    }

    public static OrderEnterNameCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        orderStepService.updateOrderStepByChatId(chatId, this);
        clientActionService.updateActionByChatId(chatId, new ClientAction("order=enter-client-name"));

        telegramService.sendMessage(new MessageSend(chatId, "Enter your name", createKeyboard(false)));

        sendCurrentName(chatId);
    }

    private void sendCurrentName(Long chatId) {
        Order order = orderStepService.findCachedOrderByChatId(chatId);

        if (StringUtils.nonWhitespaceString(order.getClient().getName())) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current name: " + order.getClient().getName(), createKeyboard(true)));
        }
    }

    private ReplyKeyboardMarkup createKeyboard(boolean skipStep) {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        if (skipStep) {
            keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                    builder().text(Commands.ORDER_NEXT_STEP_COMMAND).build()
            )));
        }

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text(Commands.ORDER_CANCEL_COMMAND).build()
        )));

        return keyboardBuilder.build();
    }

    public void doEnterName(Long chatId, String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "You entered the incorrect name, try again."));
            return;
        }

        Order order = orderStepService.findCachedOrderByChatId(chatId);
        order.getClient().setName(name);
        orderStepService.updateCachedOrder(chatId, order);

        doNextCommand(chatId);
    }

    @Override
    public void doPreviousCommand(Long chatId) {
        OrderProcessCommand.getInstance().execute(chatId);
    }

    @Override
    public void doNextCommand(Long chatId) {
        OrderEnterPhoneNumberCommand.getInstance().execute(chatId);
    }

}
