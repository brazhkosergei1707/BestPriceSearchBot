package com.example.telegrambot.entity;

import com.example.telegrambot.enumeration.ChatStepEnum;
import com.example.telegrambot.enumeration.LocaleEnum;

import jakarta.persistence.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.example.telegrambot.enumeration.ChatStepEnum.VOID;


@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_chat_id", nullable = false)
    private String telegramChatId;
    @Enumerated(EnumType.STRING)
    @Column(name = "locale", nullable = false)
    private LocaleEnum locale;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_step")
    private ChatStepEnum chatStep;

    @OneToOne
    @JoinColumn(name = "active_search", referencedColumnName = "id")
    private GoodsSearch activeSearch;

    public Chat(String telegramChatId) {
        this.telegramChatId = telegramChatId;
        locale = LocaleEnum.Ukrainian;
    }

    public Chat() {

    }
    public void searchComplete() {
        activeSearch = null;
        setChatStep(VOID);
    }

    public GoodsSearch getActiveSearch() {
        return activeSearch;
    }

    public String getLocaleCode() {
        return locale.getCode();
    }

    public void setLocaleCode(LocaleEnum locale) {
        this.locale = locale;
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

    public void setTelegramChatId(String chatId) {
        this.telegramChatId = chatId;
    }

    public LocaleEnum getLocale() {
        return locale;
    }

    public void setLocale(LocaleEnum locale) {
        this.locale = locale;
    }

    public void setActiveSearch(GoodsSearch activeSearch) {
        this.activeSearch = activeSearch;
    }

    public ChatStepEnum getChatStep() {
        return chatStep;
    }

    public void setChatStep(ChatStepEnum chatStep) {
        this.chatStep = chatStep;
    }

}
