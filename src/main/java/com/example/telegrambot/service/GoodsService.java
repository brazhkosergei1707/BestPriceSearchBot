package com.example.telegrambot.service;

import com.example.telegrambot.entity.GoodsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsService {
    @Autowired
    private ChatService chatService;

    public void updateBestPriceForGoodsDetails(GoodsDetails goodsDetails, String lowerPrice) {
        goodsDetails.setBestPrice(lowerPrice);//update
        chatService.sendPriceUpdateToChat(goodsDetails);
    }
}
