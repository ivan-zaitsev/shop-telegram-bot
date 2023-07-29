package ua.ivan909020.bot.handlers.commands;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivan909020.bot.handlers.ActionHandler;
import ua.ivan909020.bot.handlers.CommandHandler;
import ua.ivan909020.bot.handlers.UpdateHandler;
import ua.ivan909020.bot.handlers.commands.registries.CommandHandlerRegistry;
import ua.ivan909020.bot.models.domain.Button;
import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.models.domain.ClientOrder;
import ua.ivan909020.bot.models.domain.Command;
import ua.ivan909020.bot.repositories.ClientActionRepository;
import ua.ivan909020.bot.repositories.ClientCommandStateRepository;
import ua.ivan909020.bot.repositories.ClientOrderStateRepository;

public class OrderEnterPhoneNumberCommandHandler implements CommandHandler, UpdateHandler, ActionHandler {

    private static final String ENTER_PHONE_NUMBER_ACTION = "order=enter-client-phone-number";

    private static final Pattern PHONE_NUMBER_PATTERN =
            Pattern.compile("^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$");

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final ClientActionRepository clientActionRepository;
    private final ClientCommandStateRepository clientCommandStateRepository;
    private final ClientOrderStateRepository clientOrderStateRepository;

    public OrderEnterPhoneNumberCommandHandler(
            CommandHandlerRegistry commandHandlerRegistry,
            ClientActionRepository clientActionRepository,
            ClientCommandStateRepository clientCommandStateRepository,
            ClientOrderStateRepository clientOrderStateRepository) {

        this.commandHandlerRegistry = commandHandlerRegistry;
        this.clientActionRepository = clientActionRepository;
        this.clientCommandStateRepository = clientCommandStateRepository;
        this.clientOrderStateRepository = clientOrderStateRepository;
    }

    @Override
    public Command getCommand() {
        return Command.ENTER_PHONE_NUMBER;
    }

    @Override
    public void executeCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientActionRepository.updateByChatId(chatId, new ClientAction(getCommand(), ENTER_PHONE_NUMBER_ACTION));

        sendEnterNameMessage(absSender, chatId);
        sendCurrentNameMessage(absSender, chatId);
    }

    private void sendEnterNameMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Enter your phone number or press button")
                .replyMarkup(buildReplyKeyboardMarkup(false))
                .build();
        absSender.execute(message);
    }

    private void sendCurrentNameMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);
        if (StringUtils.isBlank(clientOrder.getPhoneNumber())) {
            return;
        }

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Current phone number: " + clientOrder.getPhoneNumber())
                .replyMarkup(buildReplyKeyboardMarkup(true))
                .build();
        absSender.execute(message);
    }

    private ReplyKeyboardMarkup buildReplyKeyboardMarkup(boolean skip) {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        if (skip) {
            keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                    KeyboardButton.builder().text(Button.ORDER_STEP_NEXT.getAlias()).build()
                    )));
        }

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                KeyboardButton.builder().text(Button.SEND_PHONE_NUMBER.getAlias()).requestContact(true).build()
                )));

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                KeyboardButton.builder().text(Button.ORDER_STEP_CANCEL.getAlias()).build(),
                KeyboardButton.builder().text(Button.ORDER_STEP_PREVIOUS.getAlias()).build()
                )));
        return keyboardBuilder.build();
    }

    @Override
    public boolean canHandleUpdate(Update update) {
        if (!update.hasMessage()) {
            return false;
        }

        ClientAction clientAction = clientActionRepository.findByChatId(update.getMessage().getChatId());
        if (clientAction == null || !ENTER_PHONE_NUMBER_ACTION.equals(clientAction.getAction())) {
            return false;
        }
        return update.getMessage().hasContact();
    }

    @Override
    public void handleUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String phoneNumber = update.getMessage().getContact().getPhoneNumber();

        handlePhoneNumber(absSender, update, chatId, phoneNumber);
    }

    @Override
    public boolean canHandleAction(Update update, String action) {
        return update.hasMessage() && update.getMessage().hasText() && ENTER_PHONE_NUMBER_ACTION.equals(action);
    }

    @Override
    public void handleAction(AbsSender absSender, Update update, String action) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        handlePhoneNumber(absSender, update, chatId, text);
    }

    private void handlePhoneNumber(AbsSender absSender, Update update, Long chatId, String text)
            throws TelegramApiException {
        
        if (Button.ORDER_STEP_NEXT.getAlias().equals(text)) {
            executeNextCommand(absSender, update, chatId);
            return;
        }
        if (!PHONE_NUMBER_PATTERN.matcher(text).find()) {
            sendNotCorrectPhoneNumberMessage(absSender, chatId);
            return;
        }

        saveClientOrderState(chatId, text);
        executeNextCommand(absSender, update, chatId);
    }

    private void sendNotCorrectPhoneNumberMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("You entered the incorrect phone number, try again or press button.")
                .build();
        absSender.execute(message);
    }

    private void saveClientOrderState(Long chatId, String text) {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);
        clientOrder.setPhoneNumber(text);
        clientOrderStateRepository.updateByChatId(chatId, clientOrder);
    }

    private void executeNextCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientCommandStateRepository.pushByChatId(chatId, getCommand());
        commandHandlerRegistry.find(Command.ENTER_CITY).executeCommand(absSender, update, chatId);
    }

}
