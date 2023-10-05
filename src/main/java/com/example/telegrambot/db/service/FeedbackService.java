package com.example.telegrambot.db.service;

import com.example.telegrambot.db.repository.FeedbackRepository;
import com.example.telegrambot.entity.Feedback;
import com.example.telegrambot.entity.GoodsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    public Feedback createFeedback(Feedback entity) {
        Feedback newEntity = repository.save(entity);
        return newEntity;
    }
}
