package com.example.telegrambot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardService {

//    private static ReplyKeyboardMarkup getInlineKeyboardMarkup(int numberOfLines) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        keyboardFirstRow.add(getLanguagesCommand(language));
//        keyboardFirstRow.add(getUnitsCommand(language));
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        keyboardSecondRow.add(getAlertsCommand(language));
//        keyboardSecondRow.add(getBackCommand(language));
//        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        return replyKeyboardMarkup;
//    }

    public ReplyKeyboardMarkup getTestKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton test = new KeyboardButton("Test");
        KeyboardButton test1 = new KeyboardButton("Test1");
        row.add(test);
        row.add(test1);
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
//
//    private static ReplyKeyboardMarkup getLanguagesKeyboard(String language) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        for (String languageName : LocalisationService.getSupportedLanguages().stream().map(
//                LocalisationService.Language::getName).collect(Collectors.toList())) {
//            KeyboardRow row = new KeyboardRow();
//            row.add(languageName);
//            keyboard.add(row);
//        }
//        KeyboardRow row = new KeyboardRow();
//        row.add(getCancelCommand(language));
//        keyboard.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        return replyKeyboardMarkup;
//    }





}
