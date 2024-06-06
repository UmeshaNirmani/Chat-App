package com.projects.chat_app.chatThread;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatThreadRepository extends MongoRepository<ChatThread, String> {
    Optional<ChatThread> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
