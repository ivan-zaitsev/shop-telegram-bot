package ua.ivan909020.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import ua.ivan909020.bot.core.ConfigReader;
import ua.ivan909020.bot.core.TelegramBot;
import ua.ivan909020.bot.handlers.ActionHandler;
import ua.ivan909020.bot.handlers.CommandHandler;
import ua.ivan909020.bot.handlers.UpdateHandler;
import ua.ivan909020.bot.handlers.commands.CartCommandHandler;
import ua.ivan909020.bot.handlers.commands.CatalogCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderConfirmCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderEnterAddressCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderEnterCityCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderEnterNameCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderEnterPhoneNumberCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderStepCancelCommandHandler;
import ua.ivan909020.bot.handlers.commands.OrderStepPreviousCommandHandler;
import ua.ivan909020.bot.handlers.commands.StartCommandHandler;
import ua.ivan909020.bot.handlers.commands.registries.CommandHandlerRegistry;
import ua.ivan909020.bot.handlers.commands.registries.CommandHandlerRegistryDefault;
import ua.ivan909020.bot.repositories.CartRepository;
import ua.ivan909020.bot.repositories.CategoryRepository;
import ua.ivan909020.bot.repositories.ClientActionRepository;
import ua.ivan909020.bot.repositories.ClientCommandStateRepository;
import ua.ivan909020.bot.repositories.ClientOrderStateRepository;
import ua.ivan909020.bot.repositories.ClientRepository;
import ua.ivan909020.bot.repositories.OrderRepository;
import ua.ivan909020.bot.repositories.ProductRepository;
import ua.ivan909020.bot.repositories.database.CategoryRepositoryDefault;
import ua.ivan909020.bot.repositories.database.ClientRepositoryDefault;
import ua.ivan909020.bot.repositories.database.OrderRepositoryDefault;
import ua.ivan909020.bot.repositories.database.ProductRepositoryDefault;
import ua.ivan909020.bot.repositories.memory.CartRepositoryDefault;
import ua.ivan909020.bot.repositories.memory.ClientActionRepositoryDefault;
import ua.ivan909020.bot.repositories.memory.ClientCommandStateRepositoryDefault;
import ua.ivan909020.bot.repositories.memory.ClientOrderStateRepositoryDefault;
import ua.ivan909020.bot.services.MessageService;
import ua.ivan909020.bot.services.NotificationService;
import ua.ivan909020.bot.services.impl.MessageServiceDefault;
import ua.ivan909020.bot.services.impl.NotificationServiceDefault;

public class Application {

    private ConfigReader configReader = ConfigReader.getInstance();

    private ClientActionRepository clientActionRepository;
    private ClientCommandStateRepository clientCommandStateRepository;
    private ClientOrderStateRepository clientOrderStateRepository;
    private CartRepository cartRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;

    private MessageService messageService;
    private NotificationService notificationService;

    private CommandHandlerRegistry commandHandlerRegistry;
    private List<CommandHandler> commandHandlers;
    private List<UpdateHandler> updateHandlers;
    private List<ActionHandler> actionHandlers;

    private void initializeRepositories() {
        clientActionRepository = new ClientActionRepositoryDefault();
        clientCommandStateRepository = new ClientCommandStateRepositoryDefault();
        clientOrderStateRepository = new ClientOrderStateRepositoryDefault();
        cartRepository = new CartRepositoryDefault();
        categoryRepository = new CategoryRepositoryDefault();
        productRepository = new ProductRepositoryDefault();
        orderRepository = new OrderRepositoryDefault();
        clientRepository = new ClientRepositoryDefault();
    }

    private void initializeServices() {
        messageService = new MessageServiceDefault();
        notificationService = new NotificationServiceDefault(configReader);
    }

    private void initializeCommandHandlers() {
        commandHandlerRegistry = new CommandHandlerRegistryDefault();
        commandHandlers = new ArrayList<>();

        commandHandlers.add(new CatalogCommandHandler(commandHandlerRegistry, categoryRepository, productRepository,
                cartRepository, messageService));

        commandHandlers.add(new CartCommandHandler(commandHandlerRegistry, clientCommandStateRepository,
                clientOrderStateRepository, cartRepository, clientRepository, messageService));

        commandHandlers.add(new OrderEnterNameCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        commandHandlers.add(new OrderEnterPhoneNumberCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        commandHandlers.add(new OrderEnterCityCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        commandHandlers.add(new OrderEnterAddressCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        commandHandlers.add(new OrderConfirmCommandHandler(clientActionRepository, clientCommandStateRepository,
                clientOrderStateRepository, cartRepository, orderRepository, clientRepository, messageService,
                notificationService));

        commandHandlerRegistry.setCommandHandlers(commandHandlers);
    }

    private void initializeUpdateHandlers() {
        updateHandlers = new ArrayList<>();

        updateHandlers.add(new StartCommandHandler(clientRepository, messageService));

        updateHandlers.add(new CatalogCommandHandler(commandHandlerRegistry, categoryRepository, productRepository,
                cartRepository, messageService));

        updateHandlers.add(new CartCommandHandler(commandHandlerRegistry, clientCommandStateRepository,
                clientOrderStateRepository, cartRepository, clientRepository, messageService));

        updateHandlers.add(new OrderStepCancelCommandHandler(clientActionRepository, clientCommandStateRepository,
                clientOrderStateRepository));

        updateHandlers.add(new OrderStepPreviousCommandHandler(commandHandlerRegistry, clientCommandStateRepository));

        updateHandlers.add(new OrderEnterPhoneNumberCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));
    }

    private void initializeActionHandlers() {
        actionHandlers = new ArrayList<>();

        actionHandlers.add(new OrderEnterNameCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        actionHandlers.add(new OrderEnterPhoneNumberCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        actionHandlers.add(new OrderEnterCityCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        actionHandlers.add(new OrderEnterAddressCommandHandler(commandHandlerRegistry, clientActionRepository,
                clientCommandStateRepository, clientOrderStateRepository));

        actionHandlers.add(new OrderConfirmCommandHandler(clientActionRepository, clientCommandStateRepository,
                clientOrderStateRepository, cartRepository, orderRepository, clientRepository, messageService,
                notificationService));
    }

    public static void main(String[] args) throws TelegramApiException {
        Application application = new Application();
        application.initializeRepositories();
        application.initializeServices();
        application.initializeCommandHandlers();
        application.initializeUpdateHandlers();
        application.initializeActionHandlers();

        TelegramBot telegramBot = new TelegramBot(application.configReader, application.clientActionRepository,
                application.updateHandlers, application.actionHandlers);

        new TelegramBotsApi(DefaultBotSession.class).registerBot(telegramBot);
    }

}