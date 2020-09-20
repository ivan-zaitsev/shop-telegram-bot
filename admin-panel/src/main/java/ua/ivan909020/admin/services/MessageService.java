package ua.ivan909020.admin.services;

import ua.ivan909020.admin.domain.Message;

import java.util.List;

public interface MessageService {

    Message findById(Integer id);

    Message update(Message message);

    List<Message> findAll();

}
