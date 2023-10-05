package com.example.telegrambot.local;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class BotResourceBundleMessageSource extends ResourceBundleMessageSource {
    public BotResourceBundleMessageSource() {
        this.setBasename("messages");
        this.setDefaultEncoding("utf-8");
    }
}
