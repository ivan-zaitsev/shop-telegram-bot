package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.domain.entities.Message;
import ua.ivan909020.bot.repositories.MessageRepository;
import ua.ivan909020.bot.repositories.impl.MessageRepositoryDefault;
import ua.ivan909020.bot.services.MessageService;
import ua.ivan909020.bot.utils.ClonerUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageServiceCached implements MessageService {

    private static final MessageService INSTANCE = new MessageServiceCached();

    private final MessageRepository repository = new MessageRepositoryDefault();

    private final ScheduledExecutorService cachedService = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, Message> cachedMessages = new HashMap<>();

    private MessageServiceCached() {
        startCacheClearTask();
    }

    public static MessageService getInstance() {
        return INSTANCE;
    }

    private void startCacheClearTask() {
        cachedService.scheduleAtFixedRate(cachedMessages::clear, 20, 20, TimeUnit.MINUTES);
    }

    @Override
    public Message findByName(String messageName) {
        if (messageName == null) {
            throw new IllegalArgumentException("MessageName should not be NULL");
        }
        Message message = cachedMessages.get(messageName);
        if (message == null) {
            message = repository.findByName(messageName);
            cachedMessages.put(messageName, message);
        }
        return ClonerUtils.cloneObject(message);
    }

}
