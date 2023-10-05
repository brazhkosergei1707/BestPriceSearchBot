package com.example.telegrambot.entity;

import com.example.telegrambot.enumeration.SearchState;

import jakarta.persistence.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table(name = "goods_search")
public class GoodsSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_to_root_goods")
    private String linkToRootGoods;
    @Column(name = "goods_details_ids")
    private String goodsDetailsIds;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private SearchState state;

    @OneToOne
    @JoinColumn(name = "activeGoods_id", referencedColumnName = "id")
    private GoodsDetails activeGoods;

    public GoodsDetails getActiveGoods() {
        return activeGoods;
    }

    public void setActiveGoods(GoodsDetails activeGoods) {
        this.activeGoods = activeGoods;
    }

    public GoodsSearch() {
        state = SearchState.INIT;
    }

    public void setState(SearchState state){
        this.state = state;
    }
    public SearchState getState(){
        return this.state;
    }

    public String getLinkToRootGoods() {
        return linkToRootGoods;
    }

    public void setLinkToRootGoods(String linkToGoods) {
        this.linkToRootGoods = linkToGoods;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List <Long> getUnusedGoodsDetailsIdsList() {
        if(goodsDetailsIds != null) {
            String activeGoodsId = activeGoods.getId().toString();
            List<Long> list = new ArrayList<>();
            for (String s : goodsDetailsIds.split(":")) {
                if(!activeGoodsId.equals(s)) {
                    list.add(Long.valueOf(s));
                }
            }
            return list;
        }
        return null;
    }

    public void setGoodsDetailsIdsList(List<GoodsDetails> goodsDetails) {
        List<String> strings = goodsDetails.stream().map(l -> l.getId().toString()).toList();
        goodsDetailsIds = String.join(":", strings);
    }

    public String getGoodsDetailsIds() {
        return goodsDetailsIds;
    }

    public void setGoodsDetailsIds(String goodsDetailsIds) {
        this.goodsDetailsIds = goodsDetailsIds;
    }
}
