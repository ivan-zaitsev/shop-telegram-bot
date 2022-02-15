package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan909020.admin.models.entities.Message;
import ua.ivan909020.admin.repositories.MessageRepository;
import ua.ivan909020.admin.services.MessageService;

import java.util.List;

@Service
public class MessageServiceDefault implements MessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageServiceDefault(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Message should not be NULL");
        }

        return repository.findById(id).orElse(null);
    }

    @Override
    public Message update(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message should not be NULL");
        }

        return repository.save(message);
    }

    @Override
    public List<Message> findAll() {
        return repository.findAll();
    }

}
