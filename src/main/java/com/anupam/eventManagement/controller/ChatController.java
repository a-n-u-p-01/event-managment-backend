package com.anupam.eventManagement.controller;

import com.anupam.eventManagement.entity.ChatMessage;
import com.anupam.eventManagement.entity.MessageType;
import com.anupam.eventManagement.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@Slf4j
public class ChatController {
    private final Set<String> users = new CopyOnWriteArraySet<>();
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @Autowired
    private ChatRepository chatRepository;



    @MessageMapping("/chat.sendMessage/{eventId}") //receive from
    @SendTo("/topic/public") // send to
    public ChatMessage sendMessage(@DestinationVariable Long eventId,@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(new Date());
        chatMessage.setType(MessageType.MESSAGE);
        chatMessage.setEventId(eventId);
        chatRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")//receive from
    @SendTo("/topic/public")//send to
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String userName = chatMessage.getSender();
        users.add(userName);
        headerAccessor.getSessionAttributes().put("userName", userName);
        log.info("User joined: {}", userName);

        // Broadcast updated user list
        broadcastActiveUsers();
        return chatMessage;
    }

    @MessageMapping("/chat.removeUser")
    public void removeUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String userName = chatMessage.getSender();
        users.remove(userName);
        log.info("User left: {}", userName);

        // Broadcast updated user list
        broadcastActiveUsers();
    }

    private void broadcastActiveUsers() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(MessageType.USER_LIST);
        chatMessage.setActiveUsers(users);
        messagingTemplate.convertAndSend("/topic/users", chatMessage);
    }

    @GetMapping("chat/get-messages")
    public ResponseEntity<List<ChatMessage>> getMessages(){
        return new ResponseEntity<>(chatRepository.findAll(),HttpStatus.OK);
    }

}
