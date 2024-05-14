package com.chatop.portal.controller;

import com.chatop.portal.model.Message;
import com.chatop.portal.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Post a new message", description = "Allow the current authenticated user to post a new message on another user's rental")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) {
      JSONObject responseJson = new JSONObject();
      responseJson.put("message", messageService.saveMessage(message));
      return ResponseEntity.ok(responseJson.toString());
    }
}
