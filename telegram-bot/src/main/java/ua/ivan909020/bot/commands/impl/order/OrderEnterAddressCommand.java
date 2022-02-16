package ua.ivan909020.bot.commands.impl.order;

import com.mchange.v2.lang.StringUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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

public class OrderEnterAddressCommand implements CommandSequence<Long> {

    private static final OrderEnterAddressCommand INSTANCE = new OrderEnterAddressCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("[a-zA-Z]");

    private OrderEnterAddressCommand() {
    }

    public static OrderEnterAddressCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        orderStepService.updateOrderStepByChatId(chatId, this);
        clientActionService.updateActionByChatId(chatId, new ClientAction("order=enter-client-address"));

        telegramService.sendMessage(new MessageSend(chatId, "Enter your address", createKeyboard(false)));

        sendCurrentAddress(chatId);
    }

    private void sendCurrentAddress(Long chatId) {
        Order order = orderStepService.findCachedOrderByChatId(chatId);

        if (StringUtils.nonWhitespaceString(order.getClient().getAddress())) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current address: " + order.getClient().getAddress(), createKeyboard(true)));
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
                builder().text(Commands.ORDER_CANCEL_COMMAND).build(),
                builder().text(Commands.ORDER_PREVIOUS_STEP_COMMAND).build()
        )));

        return keyboardBuilder.build();
    }

    public void doEnterAddress(Long chatId, String address) {
        Matcher matcher = ADDRESS_PATTERN.matcher(address);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "You entered the incorrect address, try again."));
            return;
        }

        Order order = orderStepService.findCachedOrderByChatId(chatId);
        order.getClient().setAddress(address);
        orderStepService.updateCachedOrder(chatId, order);

        executeNext(chatId);
    }

    @Override
    public void executePrevious(Long chatId) {
        OrderEnterCityCommand.getInstance().execute(chatId);
    }

    @Override
    public void executeNext(Long chatId) {
        OrderCreateCommand.getInstance().execute(chatId);
    }

}
