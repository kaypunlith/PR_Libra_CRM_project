package com.ut.nlSystemAPi.model.response.Telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TelegramResponse implements Serializable {

    private Boolean ok;

    @JsonProperty("result")
    private TelegramResultResponse telegramResultResponse;

    public TelegramResponse() {
    }

    public TelegramResponse(Boolean success, TelegramResultResponse telegramResultResponse) {
        this.ok = success;
        this.telegramResultResponse = telegramResultResponse;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public TelegramResultResponse gettelegramResultResponse() {
        return telegramResultResponse;
    }

    public void settelegramResultResponse(TelegramResultResponse telegramResultResponse) {
        this.telegramResultResponse = telegramResultResponse;
    }
}
