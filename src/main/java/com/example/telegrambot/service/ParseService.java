package com.example.telegrambot.service;


import com.example.telegrambot.db.ChatStorage;
import com.example.telegrambot.entity.GoodsDetails;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParseService {

    @Autowired
    private ChatStorage storage;

    public List<GoodsDetails> getDetailsForGoods(String linkToGoods) throws IOException {
        List<GoodsDetails> res = new ArrayList<>();
        String price               = "";
        String name                = "";
        String linkToShopSelection = "";

        String rootShop = "https://hotline.ua";
        Document doc = Jsoup.connect(linkToGoods)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .get();

        Element diffSpecificationTable = doc.getElementsByClass("diff-specifications-table").first();
        Element listBody = doc.getElementsByClass("list-body").first();

        if (diffSpecificationTable != null) {
            Element  tbody = diffSpecificationTable.getElementsByTag("tbody").first();
            Elements tableRows = tbody.getElementsByTag("tr");
            for (int i = 0; i < tableRows.size(); i++) {
                Elements cells = tableRows.get(i).getElementsByTag("td");
                name =  cells.get(0).getElementsByTag("a").first().text();
                linkToShopSelection = cells.get(0).getElementsByTag("a").attr("href");
                price = cells.get(1).getElementsByClass("price__value").text();

                if(!linkToShopSelection.contains(rootShop)) {
                    linkToShopSelection = rootShop + linkToShopSelection;
                }
                GoodsDetails goodsDetails = storage.createAndSaveNewGoodsDetails(name, price, linkToShopSelection);
                res.add(goodsDetails);
            }
        } else if (listBody != null) {
            Elements listItems = listBody.getElementsByClass("list-item");
            for(int i = 0; i < listItems.size(); i++) {
                Element item = listItems.get(i);
                Element listItemTitle = item.getElementsByClass("list-item__title").first();
                name                = listItemTitle.text();
                linkToShopSelection = listItemTitle.attr("href");
                Element price__value = item.getElementsByClass("price__value").first();
                if(price__value!=null) {
                    price = price__value.text();
                }

                if(!linkToShopSelection.contains(rootShop)) {
                    linkToShopSelection = rootShop + linkToShopSelection;
                }
                GoodsDetails goodsDetails = storage.createAndSaveNewGoodsDetails(name, price, linkToShopSelection);
                res.add(goodsDetails);
            }
        } else {
            price               = doc.getElementsByClass("price__value").first().text();
            name                = doc.getElementsByClass("title__main").first().text();
            linkToShopSelection = linkToGoods;
            if(!linkToShopSelection.contains(rootShop)) {
                linkToShopSelection = rootShop + linkToShopSelection;
            }
            GoodsDetails goodsDetails = storage.createAndSaveNewGoodsDetails(name, price, linkToShopSelection);
            res.add(goodsDetails);
        }
        return res;
    }


    public String getLinkToRootGoods(String goodsName) {
        String link;
        try {
            String searchString = goodsName.replace(" ","+");
            searchString +="купить+hotline";
            Document doc = Jsoup.connect("https://www.google.com/search?q="+searchString+"&sourceid=chrome&ie=UTF-8")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .get();

            String hotlineHref = doc.getElementsByAttributeValueContaining("href","hotline.ua").attr("href");
            int beginIndex = hotlineHref.indexOf("url?q=") + 6;
            int endIndex   = hotlineHref.indexOf("&sa");
            link = hotlineHref.substring(beginIndex,endIndex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return link;
    }
}
