package com.ut.nlSystemAPi.helper.Telegram;

public class CheckNull {
    public static String safe(String value) {
        return value != null ? value : "";
    }
}
