package ua.ivan909020.admin.services;

import java.util.List;

import ua.ivan909020.admin.models.entities.Message;

public interface MessageService {

    Message findById(Integer id);

    Message update(Message message);

    List<Message> findAll();

}
