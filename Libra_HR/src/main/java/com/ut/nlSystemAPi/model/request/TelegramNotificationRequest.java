package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TelegramNotificationRequest {

    @ApiModelProperty(position = 10)
    private String name;

    @ApiModelProperty(position = 20)
    private String chatId;

    @ApiModelProperty(position = 30)
    private List<Long> departments;

}
