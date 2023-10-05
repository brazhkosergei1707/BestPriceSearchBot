package com.example.telegrambot.service;

import com.example.telegrambot.entity.Chat;
import com.example.telegrambot.local.BotResourceBundleMessageSource;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LocaleService {
    @Autowired
    BotResourceBundleMessageSource messageSource;

    public String getDisplayName(String key, Chat chat, @Nullable Object[] args ) {
        String locale = chat!=null ? chat.getLocaleCode() : "en_US";
        return messageSource.getMessage(key, args,
                LocaleUtils.toLocale(locale));
    }
}
