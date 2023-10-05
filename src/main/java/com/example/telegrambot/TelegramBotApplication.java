package com.example.telegrambot;

import com.example.telegrambot.service.UpdateService;
import jakarta.activation.DataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TelegramBotApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(context.getBean(Bot.class));

        UpdateService updateService = context.getBean(UpdateService.class);
        updateService.start();
    }
}
