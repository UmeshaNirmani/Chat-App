package com.projects.chat_app.chatThread;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatThreadService {

    private final ChatThreadRepository chatThreadRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        return chatThreadRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatThread::getChatId)
                .or(() -> {
                    if(createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }

                    return  Optional.empty();
                });
    }

    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatThread senderRecipient = ChatThread
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatThread recipientSender = ChatThread
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatThreadRepository.save(senderRecipient);
        chatThreadRepository.save(recipientSender);

        return chatId;
    }
}
