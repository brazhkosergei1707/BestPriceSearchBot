package com.example.telegrambot.enumeration;

import java.util.Arrays;

public enum BotСallBackQuery {
    StartSearch("startsearch"),
    SearchGoodsDetails(":searchgoodsdetails"),
    SearchRegularity(":searchregularity"),
    ExitSearch("exitsearch"),
    ShowAllSearches("showallsearches"),
    StopMonitoringAction(":stopmonitoringaction"),

    EnterSetting("enterSetting"),
    HelpAction("helpaction"),
    OkButton("OkButton"),
    ChangeLocaleAction("ChangeLocaleAction"),
    SelectLocaleAction(":selectLocaleAction"),
    ExitSetting("exitSetting"),
    LeaveFeedback("leavefeedback");

    private String code;
    private String itemId;

    BotСallBackQuery(String code) {
        this.code = code;
        this.itemId = "";
    }

    public String getCode() {
        return code;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return itemId + code;
    }

    public static BotСallBackQuery parseBotСallBackQueryEnum(String code) {
        BotСallBackQuery res;
        if(code.contains(":")) {
            String[] split = code.split(":");
            res = getBotСallBackQueryEnum(":" + split[1]);
            res.setItemId(split[0]);
        } else {
            res = getBotСallBackQueryEnum(code);
        }
        return res;
    }

    private static BotСallBackQuery getBotСallBackQueryEnum(String code){
        return Arrays.stream(BotСallBackQuery.values()).filter(elt -> elt.code.equals(code)).findFirst().get();
    }
}
