package com.chatop.portal.service;

import com.chatop.portal.model.Message;
import com.chatop.portal.repository.MessageRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public String saveMessage(Message message) {
        Message savedMessage= messageRepository.save(message);
        return savedMessage.getMessage();
    }
}
