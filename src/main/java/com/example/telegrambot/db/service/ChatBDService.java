package com.example.telegrambot.db.service;

import com.example.telegrambot.db.repository.ChatRepository;
import com.example.telegrambot.entity.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChatBDService {

    @Autowired
    private ChatRepository repository;

    public Chat getChat(String telegramChatId) {
        List<Chat> results = repository.findByTelegramChatId(telegramChatId);
        if(!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    public Chat getChatById(Long id) throws Exception {
        Optional<Chat> employee = repository.findById(id);
        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new Exception("No employee record exist for given id");
        }
    }

    public Chat createChat(Chat entity) {
        Chat newEntity = repository.save(entity);
        return newEntity;
    }

    public Chat updateChat(Chat entity) {
        Chat newEntity = repository.save(entity);
//        Optional<Chat> employee = repository.findById(entity.getId());
//        if (employee.isPresent()) {
//            newEntity = employee.get();
//            newEntity.setLocale(entity.getLocale());
//            newEntity.setActiveSearch(entity.getActiveSearch());
//            newEntity = repository.save(newEntity);
//            return newEntity;
//        }
        return newEntity;
    }
}
