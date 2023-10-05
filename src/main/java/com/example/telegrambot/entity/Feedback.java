package com.example.telegrambot.entity;


import jakarta.persistence.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_chat_id", nullable = false)
    private String telegramChatId;

    @Column(name = "message")
    private String message;

    public Feedback() {
    }

    public Feedback(String telegramChatId, String message) {
        this.telegramChatId = telegramChatId;
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
