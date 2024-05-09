package com.chatop.portal.controller;

import com.chatop.portal.model.Message;
import com.chatop.portal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public String createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
