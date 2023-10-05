package com.example.telegrambot.service;

import com.example.telegrambot.Bot;
import com.example.telegrambot.db.ChatStorage;
import com.example.telegrambot.entity.Chat;
import com.example.telegrambot.entity.GoodsDetails;
import com.example.telegrambot.entity.GoodsSearch;
import com.example.telegrambot.enumeration.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static com.example.telegrambot.enumeration.ChatStepEnum.*;

@Component
public class ChatService {
    private Logger logger = LogManager.getLogger(ChatService.class);

    @Autowired
    private ChatStorage storage;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private KeyboardService keyboardService;
    @Autowired
    private ParseService parseService;
    @Autowired
    private ApplicationContext applicationContext;

    public void manageChannelPost() {
        //todo
        // check git commit
    }

    public void manageMessageUpdate(Chat chat, String text, SendMessage message, StringBuilder response) {
        GoodsSearch activeSearch = chat.getActiveSearch();
        if(LeaveFeedback == chat.getChatStep()) {
            storage.createAndSaveNewFeedback(chat.getTelegramChatId(), text);
            response.append( localeService.getDisplayName("response.text.thanksForFeedback",chat,null));
            chat.setChatStep(Setting);
            keyboardService.setSettingKeyboardMarkup(message, chat);
        }

        if (activeSearch != null) {
            switch (activeSearch.getState()) {
                case INIT: {

                }
                break;
                case SearchInput: {
                    String linkToRootGoods = parseService.getLinkToRootGoods(text);
                    activeSearch.setLinkToRootGoods(linkToRootGoods);
                    String [] arg = {linkToRootGoods};
                    response.append( localeService.getDisplayName("response.text.IsThisCorrectOne",chat,arg));

                    List<GoodsDetails> detailsForGoods = null;
                    try {
                        detailsForGoods = parseService.getDetailsForGoods(activeSearch.getLinkToRootGoods());
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        chat.searchComplete();
                        response.append(localeService.getDisplayName("response.text.error.somethingWrong",chat,null));
                        message.setReplyMarkup(keyboardService.getExceptionFeedbackKeyboardMarkup(chat));
                        return;
                    }

                    activeSearch.setGoodsDetailsIdsList(detailsForGoods);
                    InlineKeyboardMarkup markupInline = keyboardService.getInlineKeyboardMarkup(detailsForGoods.size() + 1);
                    if(detailsForGoods.size() > 1) {
                        response.append(localeService.getDisplayName("response.text.selectCorrectGoodsToAnalysis",chat,null));
                        for(int i = 0;i<detailsForGoods.size();i++) {
                            markupInline.getKeyboard().get(i).add(keyboardService.getGoodsDetailButton(detailsForGoods.get(i)));
                        }
                    } else if(detailsForGoods.size() == 1) {
                        response.append(localeService.getDisplayName("response.text.clickAndConfirmPriceAnalysis",chat,null));
                        markupInline.getKeyboard().get(0).add(keyboardService.getGoodsDetailButton(detailsForGoods.get(0)));
                    } else {
                        response.append(localeService.getDisplayName("response.text.NoResults",chat,null));
                    }
                    markupInline.getKeyboard().get(detailsForGoods.size()).add(keyboardService.getExitButton(chat));
                    message.setReplyMarkup(markupInline);
                    activeSearch.setState(SearchState.ExampleSent);
                }
                break;
            }
        }
    }

    public void manageCallbackQuery(Chat chat, BotСallBackQuery callBackQuery, SendMessage message, StringBuilder response, String chatID) {
        GoodsSearch activeSearch = chat.getActiveSearch();
        if (activeSearch != null) {
            switch (callBackQuery) {
                case SearchGoodsDetails: {
                    manageSearchGoodsDetailsSelection(callBackQuery, activeSearch, chatID, response, message, chat);
                } break;
                case SearchRegularity: {
                    manageSearchRegularitySelection(callBackQuery, activeSearch, response, message, chat);
                } break;
                case ExitSearch: {
                    chat.searchComplete();//Exit search
                    setInitKeyboardMarkupWithMessage(message, response, chat);
                } break;
            }
        } else {
            switch (callBackQuery) {
                case StartSearch: {
                    storage.createAndSaveNewSearch(chat);
                    activeSearch = chat.getActiveSearch();
                    activeSearch.setState(SearchState.SearchInput);
                    response.append(localeService.getDisplayName("response.text.sendSearchRequest",chat,null));
                } break;
                case ShowAllSearches: {
                    response.append(localeService.getDisplayName("response.text.YourCurrentSearchesList",chat,null));
                    List<GoodsDetails> allActiveGoodsDetailsByChatId = storage.getAllActiveGoodsDetailsByTelegramChatId(chatID);
                    if(!allActiveGoodsDetailsByChatId.isEmpty()) {

                        for(int i = 0; i< allActiveGoodsDetailsByChatId.size() ; i++) {
                            GoodsDetails gd = allActiveGoodsDetailsByChatId.get(i);
                            response.append((i+1)+". ");
                            String[] arg = {gd.getName(), gd.getBestPrice(), gd.getLink()};
                            response.append(localeService.getDisplayName("response.text.YourCurrentSearchesList.Item",chat, arg));
                        }

                    } else {
                        response.append(localeService.getDisplayName("response.text.searchesIsEmpty",chat,null));
                    }
                    keyboardService.setInitKeyboardMarkup(message,chat);
                } break;
                case StopMonitoringAction: {
                    manageStopSearchAction(callBackQuery, response, message, chat);
                } break;
                case HelpAction: {
                    response.append(localeService.getDisplayName("response.text.helpText",chat,null));
                    keyboardService.setThanksKeyboardMarkup(message,chat);
                } break;

                case EnterSetting: {
                    manageEnterSettingAction(response, message, chat);
                } break;

                case ExitSetting: {
                    manageExitSettingAction(response, message, chat);
                } break;

                case ChangeLocaleAction: {
                    manageChangeLocaleAction(response, message, chat);
                } break;
                case SelectLocaleAction: {
                    manageSelectLocaleAction(callBackQuery, message, chat);
                } break;
                case LeaveFeedback: {
                    chat.setChatStep(LeaveFeedback);
                    response.append(localeService.getDisplayName("response.text.pleaseLeaveFeedback",chat,null));
                } break;
            }
        }
    }

    private void manageSearchGoodsDetailsSelection (BotСallBackQuery callBackQuery, GoodsSearch activeSearch, String chatID,
                                                    StringBuilder response, SendMessage message, Chat chat) {
        String goodsDetailsId = callBackQuery.getItemId();
        GoodsDetails goodsDetails = storage.getGoodsDetailsById(goodsDetailsId);
        goodsDetails.setTelegramChatId(chatID);
        activeSearch.setActiveGoods(goodsDetails);
        activeSearch.setState(SearchState.PriceFetched);
        String[] arg = {goodsDetails.getName(), goodsDetails.getBestPrice()};

        response.append(localeService.getDisplayName("response.text.BestPriceDetails",chat, arg));
        SearchRegularity[] values = SearchRegularity.values();
        InlineKeyboardMarkup markupInline = keyboardService.getInlineKeyboardMarkup(values.length);
        for(int i = 0 ; i < values.length; i++ ) {
            markupInline.getKeyboard().get(0).add(keyboardService.getSearchRegularityButton(values[i], chat));
        }
        message.setReplyMarkup(markupInline);
    }

    private void manageSearchRegularitySelection (BotСallBackQuery callBackQuery, GoodsSearch activeSearch, StringBuilder response,
                                                  SendMessage message, Chat chat) {
        String searchRegularityCode = callBackQuery.getItemId();
        SearchRegularity searchRegularity = SearchRegularity.valueOf(searchRegularityCode);
        GoodsDetails goodsDetails = activeSearch.getActiveGoods();
        goodsDetails.setSearchRegularity(searchRegularity);
        activeSearch.setState(SearchState.Completed);
        storage.removeUnusedGoodsDetails(activeSearch.getUnusedGoodsDetailsIdsList());
        storage.updateSearch(activeSearch);
        String[] arg = {localeService.getDisplayName("response.button.searchRegularity." + searchRegularity.getCode(),chat,null)};
        response.append(localeService.getDisplayName("response.text.RegularityConfirmation", chat, arg));
        keyboardService.setThanksKeyboardMarkup(message, chat);
        chat.searchComplete();//Search Regularity Selected
    }

    private void manageEnterSettingAction (StringBuilder response, SendMessage message, Chat chat) {
        chat.setChatStep(Setting);
        response.append(localeService.getDisplayName("response.button.setting", chat, null));
        keyboardService.setSettingKeyboardMarkup(message, chat);
    }

    private void manageExitSettingAction (StringBuilder response, SendMessage message, Chat chat) {
        chat.setChatStep(VOID);
//        keyboardService.setInitKeyboardMarkup(message, chat);
    }
    private void manageChangeLocaleAction (StringBuilder response, SendMessage message, Chat chat) {
        response.append(localeService.getDisplayName("response.text.changeLocaleAction", chat, null));
        keyboardService.setSelectLocaleKeyboardMarkup(message, chat);
    }

    private void manageSelectLocaleAction (BotСallBackQuery selectLocaleButton, SendMessage message, Chat chat) {
        LocaleEnum locale = LocaleEnum.valueOf(selectLocaleButton.getItemId());
        chat.setLocaleCode(locale);
        keyboardService.setSettingKeyboardMarkup(message, chat);
    }

    private void manageStopSearchAction (BotСallBackQuery callBackQuery, StringBuilder response, SendMessage message, Chat chat){
        String goodsDetailsId = callBackQuery.getItemId();
        GoodsDetails goodsDetails = storage.getGoodsDetailsById(goodsDetailsId);
        goodsDetails.setSearchRegularity(null);

        storage.updateGoodsDetails(goodsDetails);//StopSearchAction
        String[] arg = {goodsDetails.getName()};
        response.append(localeService.getDisplayName("response.text.stopSearchAction", chat, arg));
        keyboardService.setThanksKeyboardMarkup(message, chat);
    }

    public void setInitKeyboardMarkupWithMessage(SendMessage message, StringBuilder response, Chat chat) {
        response.append(localeService.getDisplayName("response.text.pleaseSelectAction", chat,null));
        message.setReplyMarkup(keyboardService.getInitKeyboardMarkup(chat));
    }

    public void sendPriceUpdateToChat(GoodsDetails details) {
        Bot bot = applicationContext.getBean(Bot.class);
        try {
            if(details.getTelegramChatId() != null && details.getSearchRegularity()!=null) {
                SendMessage message  = new SendMessage();
                Chat chat = storage.getChat(details.getTelegramChatId());
                message.setChatId(details.getTelegramChatId());
                StringBuilder builder = new StringBuilder();
                if(details.getBestPrice().equals(details.getOldBestPrice())) {
                    String[] arg = {details.getName(), details.getBestPrice(), details.getLinkToShopWithLowerPrice(), details.getLink()};
                    builder.append(localeService.getDisplayName("response.text.search_result_details.bestPriceNotChanged",chat, arg));
                } else {
                    String[] arg = {details.getName(), details.getBestPrice(), details.getLinkToShopWithLowerPrice(), details.getOldBestPrice(), details.getLink()};
                    builder.append(localeService.getDisplayName("response.text.search_result_details.bestPriceChanged",chat, arg));
                }
                message.setText(builder.toString());
                keyboardService.regularSearchResultButtons(message, details);
                bot.execute(message);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
