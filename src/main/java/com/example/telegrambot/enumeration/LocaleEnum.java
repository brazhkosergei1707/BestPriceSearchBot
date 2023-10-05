package com.example.telegrambot.enumeration;

public enum LocaleEnum {
    English("en_US"),
    Ukrainian("uk_UA");

    private String code;

    LocaleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
