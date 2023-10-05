package com.example.telegrambot.entity;

import com.example.telegrambot.enumeration.SearchRegularity;
import com.example.telegrambot.service.ChatService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table(name = "goods_details")
public class GoodsDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "last_update_time")
    private Long lastUpdateTime = 0l;
    @Column(name = "link")
    private String link;
    @Column(name = "link_to_shop_with_lower_price")
    private String linkToShopWithLowerPrice;
    @Column(name = "details_name")
    private String name;
    @Column(name = "best_price")
    private String bestPrice;
    @Column(name = "old_best_price")
    private String oldBestPrice;
    @Column(name = "telegram_chat_id")
    private String telegramChatId;
    @Enumerated(EnumType.STRING)
    @Column (name = "search_regularity")
    private SearchRegularity searchRegularity;
    @Column(name = "active_search")
    private boolean activeSearch;

    public GoodsDetails() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(String newBestPrice) {
        if(this.oldBestPrice == null || !this.bestPrice.equals(newBestPrice)) {
            this.oldBestPrice = this.bestPrice;
        } else {
            this.oldBestPrice = newBestPrice;
        }
        this.bestPrice = newBestPrice;
    }

    public String getOldBestPrice() {
        return oldBestPrice;
    }

    public void setOldBestPrice(String oldBestPrice) {
        this.oldBestPrice = oldBestPrice;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String chatId) {
        this.telegramChatId = chatId;
    }

    public SearchRegularity getSearchRegularity() {
        return searchRegularity;
    }

    public void setSearchRegularity(SearchRegularity searchRegularity) {
        this.activeSearch = searchRegularity != null;
        this.searchRegularity = searchRegularity;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLinkToShopWithLowerPrice() {
        return linkToShopWithLowerPrice;
    }

    public void setLinkToShopWithLowerPrice(String linkToShopWithLowerPrice) {
        this.linkToShopWithLowerPrice = linkToShopWithLowerPrice;
    }

    public boolean isActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(boolean activeSearch) {
        this.activeSearch = activeSearch;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
