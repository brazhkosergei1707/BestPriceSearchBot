package com.example.telegrambot.db.repository;

import com.example.telegrambot.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByTelegramChatId(String telegramChatId);
}
