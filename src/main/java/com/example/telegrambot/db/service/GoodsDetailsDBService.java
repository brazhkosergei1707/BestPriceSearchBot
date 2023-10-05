package com.example.telegrambot.db.service;

import com.example.telegrambot.db.repository.GoodsDetailsRepository;
import com.example.telegrambot.entity.GoodsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsDetailsDBService {
    @Autowired
    private GoodsDetailsRepository repository;

    public GoodsDetails createGoodsDetails(GoodsDetails entity) {
        GoodsDetails newEntity = repository.save(entity);
        return newEntity;
    }
    public GoodsDetails updateGoodsDetails(GoodsDetails entity) {
        GoodsDetails newEntity = repository.save(entity);
//        if (entity.getId() == null) {
//            entity = repository.save(entity);
//            return entity;
//        } else {
//            Optional<GoodsDetails> employee = repository.findById(entity.getId());
//            if (employee.isPresent()) {
//                newEntity = employee.get();
//                newEntity.setLastUpdateTime(entity.getLastUpdateTime());
//                newEntity.setGoodsDetailsId(entity.getGoodsDetailsId());
//                newEntity.setNumber(entity.getNumber());
//                newEntity.setLink(entity.getLink());
//                newEntity.setLinkToShopWithLowerPrice(entity.getLinkToShopWithLowerPrice());
//                newEntity.setLinkToShopWithLowerPrice(entity.getLinkToShopWithLowerPrice());
//                newEntity.setName(entity.getName());
//                newEntity.setLowerPrice(entity.getLowerPrice());
//                newEntity.setTelegramChatId(entity.getTelegramChatId());
//                newEntity.setSearchRegularity(entity.getSearchRegularity());
//                newEntity.setActiveSearch(entity.isActiveSearch());
//
//                newEntity = repository.save(newEntity);
//                return newEntity;
//            }
//        }
        return newEntity;
    }

    public GoodsDetails getGoodsDetailById(Long id) {
        Optional<GoodsDetails> goodsDetails = repository.findById(id);
        if(goodsDetails.isPresent()) {
            return goodsDetails.get();
        }
        return null;
    }

    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    public List<GoodsDetails> getAllActiveGoodsDetails(boolean activeSearch) {
        return repository.getAllActiveGoodsDetails(activeSearch);
    }

    public List<GoodsDetails> getAllActiveGoodsDetailsByTelegramChatId(String telegramChatId) {
        return repository.getAllActiveGoodsDetailsByTelegramChatId(telegramChatId, true);
    }
}
