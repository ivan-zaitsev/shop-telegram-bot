package ua.ivan909020.bot.commands.impl.order;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton.builder;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.models.domain.MessageSend;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.services.ClientActionService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientActionServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

public class OrderEnterCityCommand implements CommandSequence<Long> {

    private static final OrderEnterCityCommand INSTANCE = new OrderEnterCityCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private static final Pattern CITY_PATTERN = Pattern.compile("[a-zA-Z]");

    private OrderEnterCityCommand() {
    }

    public static OrderEnterCityCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        orderStepService.updateOrderStepByChatId(chatId, this);
        clientActionService.updateActionByChatId(chatId, new ClientAction("order=enter-client-city"));

        telegramService.sendMessage(new MessageSend(chatId, "Enter your city", createKeyboard(false)));

        sendCurrentCity(chatId);
    }

    private void sendCurrentCity(Long chatId) {
        Order order = orderStepService.findCachedOrderByChatId(chatId);

        if (!StringUtils.isBlank(order.getClient().getCity())) {
            telegramService.sendMessage(new MessageSend(chatId,
                    "Current city: " + order.getClient().getCity(), createKeyboard(true)));
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

    public void doEnterCity(Long chatId, String city) {
        Matcher matcher = CITY_PATTERN.matcher(city);
        if (!matcher.find()) {
            telegramService.sendMessage(new MessageSend(chatId, "You entered the incorrect city, try again."));
            return;
        }

        Order order = orderStepService.findCachedOrderByChatId(chatId);
        order.getClient().setCity(city);
        orderStepService.updateCachedOrder(chatId, order);

        executeNext(chatId);
    }

    @Override
    public void executePrevious(Long chatId) {
        OrderEnterPhoneNumberCommand.getInstance().execute(chatId);
    }

    @Override
    public void executeNext(Long chatId) {
        OrderEnterAddressCommand.getInstance().execute(chatId);
    }

}
