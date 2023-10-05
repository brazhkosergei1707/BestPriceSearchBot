package com.example.telegrambot.service;

import com.example.telegrambot.db.ChatStorage;
import com.example.telegrambot.entity.GoodsDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateService extends Thread {
    Logger logger = LogManager.getLogger(UpdateService.class);

    @Autowired
    private ChatStorage storage;
    @Autowired
    private GoodsService goodsService;

    @Override
    public void run() {
        while (true) {//TODO
            try {
            long currentTime = System.currentTimeMillis();
            for(GoodsDetails details : storage.getAllActiveGoodsDetails()) {
                long diff=0l;
                switch (details.getSearchRegularity()) {
                    case Each_Hour: {
                        diff = 3600000;
                    }break;
                    case Twice_A_Day: {
                        diff = 43200000;
                    }break;
                    case Daily: {
                        diff = 86400000;
                    }break;
                }
                if(currentTime - details.getLastUpdateTime() > diff) {
                    details.setLastUpdateTime(currentTime);
                    updateLowerPriceForGoodsDetail(details);
                    storage.updateGoodsDetails(details);
                }
            }
                updateCurrencyExchangeValues();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateLowerPriceForGoodsDetail(GoodsDetails details) {
        try {
            String rootShop = "https://hotline.ua";
            Document doc = Jsoup.connect(details.getLink())
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .get();
            String price = doc.getElementsByClass("price__value").first().text();
            Elements shops = doc.getElementsByClass("list__item");
            for (Element element : shops) {
                String priceInCurrentShop = element.getElementsByClass("price__value").text();
                if(priceInCurrentShop.equals(price)) {
                    Element shop__title = element.getElementsByClass("shop__title").first();
                    if(shop__title != null) {
                        String linkToShopWithLowerPrice = shop__title.attr("href");
                        details.setLinkToShopWithLowerPrice( rootShop  +  linkToShopWithLowerPrice);
                    }
                }
            }
            logger.info("Best price for " + details.getName() + " is "+ price);
            logger.info("Link " + details.getLink());
            logger.info("Link To Shop With Lower Price " + details.getLinkToShopWithLowerPrice() );
            goodsService.updateBestPriceForGoodsDetails(details, price);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void updateCurrencyExchangeValues() {//TODO should be implemented next
        try {
            String rootCurrencyExchangeLink = "https://finance.i.ua/market/kiev/";
            Document doc = Jsoup.connect(rootCurrencyExchangeLink)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .get();
           // System.out.println(doc);
//            String price = doc.;
//            String price = doc.getElementsByClass("price__value").first().text();
////            if(!price.equals(details.getLowerPrice())) {
//            if (true) {
//                Elements shops = doc.getElementsByClass("list__item");
//                for (Element element : shops) {
//                    String priceInCurrentShop = element.getElementsByClass("price__value").text();
//                    if(priceInCurrentShop.equals(price)) {
//                        Element shop__title = element.getElementsByClass("shop__title").first();
//                        if(shop__title != null) {
//                            String linkToShopWithLowerPrice = shop__title.attr("href");
//                            details.setLinkToShopWithLowerPrice( rootShop  +  linkToShopWithLowerPrice);
//                        }
//                    }
//                }
//                logger.info("Best price for " + details.getName() + " is "+ price);
//                logger.info("Link " + details.getLink());
//                logger.info("Link To Shop With Lower Price " + details.getLinkToShopWithLowerPrice() );
//                goodsService.updateBestPriceForGoodsDetails(details, price);
//            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
