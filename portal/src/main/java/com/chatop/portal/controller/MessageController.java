package com.chatop.portal.controller;

import com.chatop.portal.model.Message;
import com.chatop.portal.service.MessageService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) {
      JSONObject responseJson = new JSONObject();
      responseJson.put("message", messageService.saveMessage(message));
      return ResponseEntity.ok(responseJson.toString());
    }
}
