package ua.ivanzaitsev.bot.handlers.commands;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivanzaitsev.bot.handlers.ActionHandler;
import ua.ivanzaitsev.bot.handlers.CommandHandler;
import ua.ivanzaitsev.bot.models.domain.Button;
import ua.ivanzaitsev.bot.models.domain.ClientAction;
import ua.ivanzaitsev.bot.models.domain.ClientOrder;
import ua.ivanzaitsev.bot.models.domain.Command;
import ua.ivanzaitsev.bot.models.entities.Client;
import ua.ivanzaitsev.bot.models.entities.Order;
import ua.ivanzaitsev.bot.models.entities.OrderItem;
import ua.ivanzaitsev.bot.models.entities.OrderStatus;
import ua.ivanzaitsev.bot.repositories.CartRepository;
import ua.ivanzaitsev.bot.repositories.ClientActionRepository;
import ua.ivanzaitsev.bot.repositories.ClientCommandStateRepository;
import ua.ivanzaitsev.bot.repositories.ClientOrderStateRepository;
import ua.ivanzaitsev.bot.repositories.ClientRepository;
import ua.ivanzaitsev.bot.repositories.OrderRepository;
import ua.ivanzaitsev.bot.repositories.hibernate.HibernateTransactionFactory;
import ua.ivanzaitsev.bot.services.MessageService;
import ua.ivanzaitsev.bot.services.NotificationService;

public class OrderConfirmCommandHandler implements CommandHandler, ActionHandler {

    private static final String CONFIRM_ORDER_ACTION = "order=confirm";

    private final ClientActionRepository clientActionRepository;
    private final ClientCommandStateRepository clientCommandStateRepository;
    private final ClientOrderStateRepository clientOrderStateRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final MessageService messageService;
    private final NotificationService notificationService;

    public OrderConfirmCommandHandler(
            ClientActionRepository clientActionRepository,
            ClientCommandStateRepository clientCommandStateRepository,
            ClientOrderStateRepository clientOrderStateRepository,
            CartRepository cartRepository,
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            MessageService messageService,
            NotificationService notificationService) {

        this.clientActionRepository = clientActionRepository;
        this.clientCommandStateRepository = clientCommandStateRepository;
        this.clientOrderStateRepository = clientOrderStateRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.messageService = messageService;
        this.notificationService = notificationService;
    }

    @Override
    public Command getCommand() {
        return Command.ORDER_CONFIRM;
    }

    @Override
    public void executeCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        clientActionRepository.updateByChatId(chatId, new ClientAction(getCommand(), CONFIRM_ORDER_ACTION));

        sendConfirmOrderMessage(absSender, chatId);
    }

    private void sendConfirmOrderMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .text("Confirm order:")
                .replyMarkup(buildReplyKeyboardMarkup())
                .build();
        absSender.execute(message);
    }

    private ReplyKeyboardMarkup buildReplyKeyboardMarkup() {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                KeyboardButton.builder().text(Button.ORDER_CONFIRM.getAlias()).build()
                )));

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                KeyboardButton.builder().text(Button.ORDER_STEP_CANCEL.getAlias()).build(),
                KeyboardButton.builder().text(Button.ORDER_STEP_PREVIOUS.getAlias()).build()
                )));
        return keyboardBuilder.build();
    }

    @Override
    public boolean canHandleAction(Update update, String action) {
        return update.hasMessage() && update.getMessage().hasText() && CONFIRM_ORDER_ACTION.equals(action);
    }

    @Override
    public void handleAction(AbsSender absSender, Update update, String action) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if (!Button.ORDER_CONFIRM.getAlias().equals(text)) {
            sendNotCorrectConfirmOptionMessage(absSender, chatId);
            return;
        }

        completeOrder(absSender, update, chatId);
    }

    private void sendNotCorrectConfirmOptionMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("You have selected incorrect confirmation option, please press the button")
                .build();
        absSender.execute(message);
    }

    private void completeOrder(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        ClientOrder clientOrder = clientOrderStateRepository.findByChatId(chatId);

        Client client = clientRepository.findByChatId(chatId);
        client.setName(clientOrder.getClientName());
        client.setPhoneNumber(clientOrder.getPhoneNumber());
        client.setCity(clientOrder.getCity());
        client.setAddress(clientOrder.getAddress());

        Order order = new Order();
        order.setClient(client);
        order.setCreatedDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setAmount(clientOrder.calculateTotalPrice());
        order.setItems(clientOrder.getCartItems().stream().map(OrderItem::from).toList());

        HibernateTransactionFactory.inTransactionVoid(session -> {
            orderRepository.save(order);
            clientRepository.update(order.getClient());
        });

        sendOrderMessage(absSender, chatId);
        clearClientOrderState(chatId);

        notificationService.notifyAdminChatAboutNewOrder(absSender, order);
    }

    private void sendOrderMessage(AbsSender absSender, Long chatId) throws TelegramApiException {
        String text = messageService.findByName("ORDER_CREATED_MESSAGE").buildText();

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(Button.createGeneralMenuKeyboard())
                .build();
        absSender.execute(message);
    }

    private void clearClientOrderState(Long chatId) {
        clientActionRepository.deleteByChatId(chatId);
        clientCommandStateRepository.deleteAllByChatId(chatId);
        clientOrderStateRepository.deleteByChatId(chatId);
        cartRepository.deleteAllCartItemsByChatId(chatId);
    }

}
