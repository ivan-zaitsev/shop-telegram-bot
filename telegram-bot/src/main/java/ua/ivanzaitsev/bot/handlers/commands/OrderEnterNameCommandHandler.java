package ua.ivanzaitsev.bot.handlers.commands;

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

import ua.ivanzaitsev.bot.handlers.ActionHandler;
import ua.ivanzaitsev.bot.handlers.CommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.registries.CommandHandlerRegistry;
import ua.ivanzaitsev.bot.models.domain.Button;
import ua.ivanzaitsev.bot.models.domain.ClientAction;
import ua.ivanzaitsev.bot.models.domain.ClientOrder;
import ua.ivanzaitsev.bot.models.domain.Command;
import ua.ivanzaitsev.bot.repositories.ClientActionRepository;
import ua.ivanzaitsev.bot.repositories.ClientCommandStateRepository;
import ua.ivanzaitsev.bot.repositories.ClientOrderStateRepository;

public class OrderEnterNameCommandHandler implements CommandHandler, ActionHandler {

    private static final String ENTER_NAME_ACTION = "order=enter-client-name";

    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]");

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final ClientActionRepository clientActionRepository;
    private final ClientCommandStateRepository clientCommandStateRepository;
    private final ClientOrderStateRepository clientOrderStateRepository;

    public OrderEnterNameCommandHandler(
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
        return Command.ENTER_NAME;
    }

    @Override
    public void executeCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientActionRepository.updateByChatId(chatId, new ClientAction(getCommand(), ENTER_NAME_ACTION));

        sendEnterNameMessage(absSender, chatId);
        sendCurrentNameMessage(absSender, chatId);
    }

    private void sendEnterNameMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Enter your name")
                .replyMarkup(buildReplyKeyboardMarkup(false))
                .build();
        absSender.execute(message);
    }

    private void sendCurrentNameMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);
        if (StringUtils.isBlank(clientOrder.getClientName())) {
            return;
        }

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Current name: " + clientOrder.getClientName())
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
                KeyboardButton.builder().text(Button.ORDER_STEP_CANCEL.getAlias()).build()
                )));
        return keyboardBuilder.build();
    }

    @Override
    public boolean canHandleAction(Update update, String action) {
        return update.hasMessage() && update.getMessage().hasText() && ENTER_NAME_ACTION.equals(action);
    }

    @Override
    public void handleAction(AbsSender absSender, Update update, String action) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if (Button.ORDER_STEP_NEXT.getAlias().equals(text)) {
            executeNextCommand(absSender, update, chatId);
            return;
        }
        if (!NAME_PATTERN.matcher(text).find()) {
            sendNotCorrectNameMessage(absSender, chatId);
            return;
        }

        saveClientOrderState(chatId, text);
        executeNextCommand(absSender, update, chatId);
    }

    private void sendNotCorrectNameMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("You entered the incorrect name, try again.")
                .build();
        absSender.execute(message);
    }

    private void saveClientOrderState(Long chatId, String text) {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);
        clientOrder.setClientName(text);
        clientOrderStateRepository.updateByChatId(chatId, clientOrder);
    }

    private void executeNextCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientCommandStateRepository.pushByChatId(chatId, getCommand());
        commandHandlerRegistry.find(Command.ENTER_PHONE_NUMBER).executeCommand(absSender, update, chatId);
    }

}
