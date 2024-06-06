package com.projects.chat_app.chat;

import com.projects.chat_app.chatThread.ChatThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatThreadService chatThreadService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatThreadService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(); // You can create your own dedicated exception
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatThreadService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }
}
