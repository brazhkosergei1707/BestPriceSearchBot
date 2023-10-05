package com.example.telegrambot.db;

import com.example.telegrambot.db.service.ChatBDService;
import com.example.telegrambot.db.service.FeedbackService;
import com.example.telegrambot.db.service.GoodsDetailsDBService;
import com.example.telegrambot.db.service.GoodsSearchDBService;
import com.example.telegrambot.entity.Chat;
import com.example.telegrambot.entity.Feedback;
import com.example.telegrambot.entity.GoodsDetails;
import com.example.telegrambot.entity.GoodsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatStorage {
    @Autowired
    private ChatBDService chatService;
    @Autowired
    private GoodsSearchDBService searchService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private GoodsDetailsDBService goodsDetailsService;

    @Autowired
    private ApplicationContext applicationContext;
    public List<GoodsDetails> getAllActiveGoodsDetailsByTelegramChatId(String telegramChatId) {
        return goodsDetailsService.getAllActiveGoodsDetailsByTelegramChatId(telegramChatId);
    }

    public GoodsDetails getGoodsDetailsById(String goodsDetailsId) {
        return goodsDetailsService.getGoodsDetailById(Long.valueOf(goodsDetailsId));
    }

    public Chat getChat(String telegramChatId) {
        return chatService.getChat(telegramChatId);
    }

    public Chat addChat(String chatId) {
        Chat chat = chatService.createChat(applicationContext.getBean(Chat.class, chatId));
        return chat;
    }

    public List<GoodsDetails> getAllActiveGoodsDetails() {
        return goodsDetailsService.getAllActiveGoodsDetails(true);
    }

    public void createAndSaveNewSearch(Chat chat) {
        GoodsSearch goodsSearch = searchService.createGoodsSearch(applicationContext.getBean(GoodsSearch.class));
        chat.setActiveSearch(goodsSearch);
        chatService.updateChat(chat);
    }
    public void createAndSaveNewFeedback(String telegramChatId,String text) {
        feedbackService.createFeedback(applicationContext.getBean(Feedback.class, telegramChatId, text));
    }

    public GoodsSearch updateSearch(GoodsSearch goodsSearch) {
        GoodsDetails activeGoods = goodsSearch.getActiveGoods();
        if(activeGoods != null) {
            updateGoodsDetails(activeGoods);
        }
        GoodsSearch updatedGoodsSearch = searchService.updateGoodsSearch(goodsSearch);
        return updatedGoodsSearch;
    }
    public GoodsDetails updateGoodsDetails(GoodsDetails goodsDetails) {
        GoodsDetails updatedGoodsDetails = goodsDetailsService.updateGoodsDetails(goodsDetails);
        return updatedGoodsDetails;
    }

    public void updateChat(Chat chat) {
        GoodsSearch activeSearch = chat.getActiveSearch();
        if (activeSearch != null) {
            updateSearch(activeSearch);
        }
        chatService.updateChat(chat);
    }

    public GoodsDetails createAndSaveNewGoodsDetails(String name, String price, String link) {
        GoodsDetails goodsDetails = applicationContext.getBean(GoodsDetails.class);
        goodsDetails.setName(name);
        goodsDetails.setBestPrice(price);//create
        goodsDetails.setLink(link);
        return goodsDetailsService.createGoodsDetails(goodsDetails);
    }

    public void removeUnusedGoodsDetails(List<Long> ids) {
        goodsDetailsService.deleteAllById(ids);
    }
}
