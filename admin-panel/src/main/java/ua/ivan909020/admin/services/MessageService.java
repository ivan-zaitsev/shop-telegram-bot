package ua.ivan909020.admin.services;

import ua.ivan909020.admin.models.entities.Message;

import java.util.List;

public interface MessageService {

    Message findById(Integer id);

    Message update(Message message);

    List<Message> findAll();

}
