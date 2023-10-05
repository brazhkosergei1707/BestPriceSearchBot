package com.example.telegrambot.enumeration;

public enum SearchRegularity {
    Daily("once_a_day"),
    Twice_A_Day("twice_a_day"),
    Each_Hour("each_hour");

    private String code;

    SearchRegularity(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getCode() {
        return code;
    }
}
