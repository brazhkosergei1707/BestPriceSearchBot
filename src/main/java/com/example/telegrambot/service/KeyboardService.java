package com.example.telegrambot.service;

import com.example.telegrambot.db.ChatStorage;
import com.example.telegrambot.entity.Chat;
import com.example.telegrambot.entity.GoodsDetails;
import com.example.telegrambot.enumeration.BotСallBackQuery;
import com.example.telegrambot.enumeration.LocaleEnum;
import com.example.telegrambot.enumeration.SearchRegularity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.telegrambot.enumeration.BotСallBackQuery.*;

@Component
public class KeyboardService {

    @Autowired
    private LocaleService localeService;
    @Autowired
    private ChatStorage storage;
    @Autowired
    private ButtonService buttonService;
    @Autowired
    private ReplyKeyboardService replyKeyboardService;

    public void setInitKeyboardMarkup(SendMessage message, Chat chat) {
        message.setReplyMarkup(getInitKeyboardMarkup(chat));
    }
    public void setThanksKeyboardMarkup(SendMessage message, Chat chat) {
        InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup(1);
        markupInline.getKeyboard().get(0).add(getThanksButton(chat));
        message.setReplyMarkup(markupInline);
    }

    public InlineKeyboardMarkup getExceptionFeedbackKeyboardMarkup(Chat chat) {
        InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup(2);
        markupInline.getKeyboard().get(0).add(getLeaveFeedbackButton(chat));
        markupInline.getKeyboard().get(1).add(getExitButton(chat));
        return markupInline;
    }
    public InlineKeyboardMarkup getInitKeyboardMarkup(Chat chat) {
        InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup(2);
        markupInline.getKeyboard().get(0).add(getStartSearchButton(chat));
        markupInline.getKeyboard().get(0).add(getViewAllSearchesButton(chat));
        markupInline.getKeyboard().get(1).add(getSettingButton(chat));
        markupInline.getKeyboard().get(1).add(getHelpButton(chat));
        return markupInline;
    }
    public void setSettingKeyboardMarkup(SendMessage message, Chat chat) {
        InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup(3);
        markupInline.getKeyboard().get(0).add(getChangeLocaleButton(chat));
        markupInline.getKeyboard().get(1).add(getLeaveFeedbackButton(chat));
        markupInline.getKeyboard().get(2).add(getExitSettingButton(chat));
        message.setReplyMarkup(markupInline);
    }
    public void setSelectLocaleKeyboardMarkup(SendMessage message, Chat chat) {
        LocaleEnum[] values = LocaleEnum.values();
        InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup(values.length);
        for(int i = 0; i < values.length; i ++) {
            markupInline.getKeyboard().get(i).add(getSelectLocaleButton(chat,values[i]));
        }
        message.setReplyMarkup(markupInline);
    }

    public InlineKeyboardButton getStartSearchButton(Chat chat) {
        InlineKeyboardButton startSearchButton = getInlineKeyboardButton();
        startSearchButton.setText(localeService.getDisplayName("response.button.start_new_search",chat,null));
        startSearchButton.setCallbackData(BotСallBackQuery.StartSearch.toString());
        return startSearchButton;
    }

    public InlineKeyboardButton getViewAllSearchesButton(Chat chat) {
        InlineKeyboardButton startSearchButton = getInlineKeyboardButton();
        startSearchButton.setText( localeService.getDisplayName("response.button.my_searches",chat,null));
        startSearchButton.setCallbackData(BotСallBackQuery.ShowAllSearches.toString());
        return startSearchButton;
    }
    public InlineKeyboardButton getChangeLocaleButton(Chat chat) {
        InlineKeyboardButton startSearchButton = getInlineKeyboardButton();
        startSearchButton.setText( localeService.getDisplayName("response.button.selectLocale",chat,null));
        startSearchButton.setCallbackData(ChangeLocaleAction.toString());
        return startSearchButton;
    }
    public InlineKeyboardButton getSettingButton(Chat chat) {
        InlineKeyboardButton startSearchButton = getInlineKeyboardButton();
        startSearchButton.setText( localeService.getDisplayName("response.button.setting",chat,null));
        startSearchButton.setCallbackData(EnterSetting.toString());
        return startSearchButton;
    }
    public InlineKeyboardButton getSelectLocaleButton(Chat chat, LocaleEnum localeEnum) {
        InlineKeyboardButton startSearchButton = getInlineKeyboardButton();
        startSearchButton.setText( localeService.getDisplayName("response.button.selectLocale"+localeEnum.getCode(),chat,null));
        BotСallBackQuery selectLocaleButton = SelectLocaleAction;
        selectLocaleButton.setItemId(localeEnum.name());
        startSearchButton .setCallbackData(SelectLocaleAction.toString());
        return startSearchButton;
    }
    public InlineKeyboardButton getGoodsDetailButton(GoodsDetails goodsDetails) {
        InlineKeyboardButton exitButton = getInlineKeyboardButton();
        exitButton.setText(goodsDetails.getName());
        BotСallBackQuery botСallBackQuery = SearchGoodsDetails;
        botСallBackQuery.setItemId(String.valueOf(goodsDetails.getId()));
        exitButton.setCallbackData(botСallBackQuery.toString());
        return exitButton;
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkup(int numberOfLines) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for(int i = 0 ; i < numberOfLines ; i ++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private InlineKeyboardButton getInlineKeyboardButton(){
        return new InlineKeyboardButton();
    }

    public void regularSearchResultButtons(SendMessage message, GoodsDetails details) {

        Chat chat = storage.getChat(details.getTelegramChatId());

        InlineKeyboardButton stopSearchButton = getInlineKeyboardButton();
        stopSearchButton.setText( localeService.getDisplayName("response.button.StopSearch",chat,null));
        BotСallBackQuery stopMonitoringAction = StopMonitoringAction;
        stopMonitoringAction.setItemId(details.getId().toString());
        stopSearchButton.setCallbackData(stopMonitoringAction.toString());
        InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup(1);
        inlineKeyboardMarkup.getKeyboard().get(0).add(stopSearchButton);
        inlineKeyboardMarkup.getKeyboard().get(0).add(getThanksButton(chat));
        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    public InlineKeyboardButton getThanksButton(Chat chat) {
        InlineKeyboardButton okButton = getInlineKeyboardButton();
        okButton.setText(localeService.getDisplayName("response.button.Thanks",chat,null));
        okButton.setCallbackData(OkButton.toString());
        return okButton;
    }

    public InlineKeyboardButton getExitButton(Chat chat) {
        InlineKeyboardButton exitButton = getInlineKeyboardButton();
        exitButton.setText(localeService.getDisplayName("response.button.Exit", chat,null));
        exitButton.setCallbackData(ExitSearch.toString());
        return exitButton;
    }

    public InlineKeyboardButton getExitSettingButton(Chat chat) {
        InlineKeyboardButton exitButton = getInlineKeyboardButton();
        exitButton.setText(localeService.getDisplayName("response.button.Exit", chat,null));
        exitButton.setCallbackData(ExitSetting.toString());
        return exitButton;
    }
    public InlineKeyboardButton getLeaveFeedbackButton(Chat chat) {
        InlineKeyboardButton leaveFeedbackButton = getInlineKeyboardButton();
        leaveFeedbackButton.setText(localeService.getDisplayName("response.button.leaveFeedback", chat,null));
        leaveFeedbackButton.setCallbackData(LeaveFeedback.toString());
        return leaveFeedbackButton;
    }
    public InlineKeyboardButton getHelpButton(Chat chat) {
        InlineKeyboardButton helpButton = getInlineKeyboardButton();
        helpButton.setText(localeService.getDisplayName("response.button.helpButton", chat,null));
        helpButton.setCallbackData(HelpAction.toString());
        return helpButton;
    }

    public InlineKeyboardButton getSearchRegularityButton(SearchRegularity searchRegularity, Chat chat) {
        InlineKeyboardButton searchRegularityButton = getInlineKeyboardButton();

        searchRegularityButton.setText(
                localeService.getDisplayName("response.button.searchRegularity." + searchRegularity.getCode(), chat,null));

        BotСallBackQuery botСallBackQuery = SearchRegularity;
        botСallBackQuery.setItemId(searchRegularity.name());

        searchRegularityButton.setCallbackData(botСallBackQuery.toString());
        return searchRegularityButton;
    }
}
