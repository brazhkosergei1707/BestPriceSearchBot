package com.example.telegrambot;

import com.example.telegrambot.db.ChatStorage;
import com.example.telegrambot.entity.Chat;
import com.example.telegrambot.enumeration.BotСallBackQuery;
import com.example.telegrambot.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {
    Logger logger = LogManager.getLogger(Bot.class);

    @Autowired
    private ChatStorage storage;
    @Autowired
    private ChatService chatService;
    @Autowired
    private LocaleService localeService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private Long getChatId(Update update) {
        if(update.hasChannelPost()){
            return update.getChannelPost().getChatId();
        } else if(update.hasMessage()){
            return update.getMessage().getChatId();
        } else if(update.hasCallbackQuery()){
            return update.getCallbackQuery().getMessage().getChatId();
        } else if(update.hasChatJoinRequest()) {
            return update.getChatJoinRequest().getChat().getId();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getChatId();
        } else {
            return null;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage message     = new SendMessage();
            Long chatID = getChatId(update);
            if(chatID == null) {
                logger.error("Unexpected Chat ID for " + update);
                return;
            }
            message.setChatId(chatID);

            Chat chat = storage.getChat(chatID.toString());
            StringBuilder response  = new StringBuilder();
            if (chat == null) {
                storage.addChat(chatID.toString());
                chatService.setInitKeyboardMarkupWithMessage(message, response, chat);
                message.setText(response.toString());
                execute(message);
                logger.info("New Chat created " + update.getUpdateId());
                return;
            }
            logger.info("Get request " + update.getUpdateId());

            if(update.hasChannelPost()) {
                chatService.manageChannelPost();
            } else if(update.hasCallbackQuery()) {
                BotСallBackQuery botСallBackQuery = BotСallBackQuery.parseBotСallBackQueryEnum(update.getCallbackQuery().getData());
                chatService.manageCallbackQuery(chat, botСallBackQuery,
                        message,response,chatID.toString());
            } else if(update.hasMessage()) {
                chatService.manageMessageUpdate(chat,
                        update.getMessage().getText(),
                        message,response);
            }
            if(response.isEmpty()) {
                chatService.setInitKeyboardMarkupWithMessage(message,response, chat);
//                response.append(localeService.getDisplayName("response.text.pingIfNeeded",chat,null));
            }

            storage.updateChat(chat);

            message.setText(response.toString());
            logger.info("Response sent " + message.getText());
            execute(message);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

}
