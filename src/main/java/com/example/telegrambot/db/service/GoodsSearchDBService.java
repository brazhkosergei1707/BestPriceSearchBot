package com.example.telegrambot.db.service;

import com.example.telegrambot.db.repository.GoodsSearchRepository;
import com.example.telegrambot.entity.GoodsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsSearchDBService {
    @Autowired
    private GoodsSearchRepository repository;
    public GoodsSearch createGoodsSearch(GoodsSearch entity) {
        GoodsSearch newEntity = repository.save(entity);
        return newEntity;
    }
    public GoodsSearch updateGoodsSearch(GoodsSearch entity) {
        GoodsSearch newEntity = repository.save(entity);
        return newEntity;
        //        Optional<Chat> employee = repository.findById(entity.getId());
//        if (employee.isPresent()) {
//            newEntity = employee.get();
//            newEntity.setLocale(entity.getLocale());
//            newEntity.setActiveSearch(entity.getActiveSearch());
//            newEntity = repository.save(newEntity);
//            return newEntity;
//        }
    }
}
