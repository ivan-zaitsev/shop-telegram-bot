package ua.ivanzaitsev.admin.services;

import java.util.List;

import ua.ivanzaitsev.admin.models.entities.Message;

public interface MessageService {

    Message findById(Integer id);

    Message update(Message message);

    List<Message> findAll();

}
