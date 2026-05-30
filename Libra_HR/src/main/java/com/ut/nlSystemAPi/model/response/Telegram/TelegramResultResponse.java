package com.ut.nlSystemAPi.model.response.Telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class TelegramResultResponse {

    @JsonProperty("message_id")
    private Long messageId;

    public TelegramResultResponse() {
    }

    public TelegramResultResponse(Long messageId) {
        this.messageId = messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }
}
