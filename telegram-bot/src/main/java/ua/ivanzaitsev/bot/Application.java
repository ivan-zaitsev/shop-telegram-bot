package ua.ivanzaitsev.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import ua.ivanzaitsev.bot.core.ConfigReader;
import ua.ivanzaitsev.bot.core.TelegramBot;
import ua.ivanzaitsev.bot.handlers.ActionHandler;
import ua.ivanzaitsev.bot.handlers.CommandHandler;
import ua.ivanzaitsev.bot.handlers.UpdateHandler;
import ua.ivanzaitsev.bot.handlers.commands.CartCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.CatalogCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderConfirmCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderEnterAddressCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderEnterCityCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderEnterNameCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderEnterPhoneNumberCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderStepCancelCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.OrderStepPreviousCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.StartCommandHandler;
import ua.ivanzaitsev.bot.handlers.commands.registries.CommandHandlerRegistry;
import ua.ivanzaitsev.bot.handlers.commands.registries.CommandHandlerRegistryDefault;
import ua.ivanzaitsev.bot.repositories.CartRepository;
import ua.ivanzaitsev.bot.repositories.CategoryRepository;
import ua.ivanzaitsev.bot.repositories.ClientActionRepository;
import ua.ivanzaitsev.bot.repositories.ClientCommandStateRepository;
import ua.ivanzaitsev.bot.repositories.ClientOrderStateRepository;
import ua.ivanzaitsev.bot.repositories.ClientRepository;
import ua.ivanzaitsev.bot.repositories.OrderRepository;
import ua.ivanzaitsev.bot.repositories.ProductRepository;
import ua.ivanzaitsev.bot.repositories.database.CategoryRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.database.ClientRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.database.OrderRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.database.ProductRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.memory.CartRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.memory.ClientActionRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.memory.ClientCommandStateRepositoryDefault;
import ua.ivanzaitsev.bot.repositories.memory.ClientOrderStateRepositoryDefault;
import ua.ivanzaitsev.bot.services.MessageService;
import ua.ivanzaitsev.bot.services.NotificationService;
import ua.ivanzaitsev.bot.services.impl.MessageServiceDefault;
import ua.ivanzaitsev.bot.services.impl.NotificationServiceDefault;

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