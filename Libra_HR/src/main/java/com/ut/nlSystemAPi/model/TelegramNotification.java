package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TelegramNotification{

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 10)
    private String name;

    @ApiModelProperty(position = 20)
    private String chatId;

    @ApiModelProperty(position = 220)
    private String created;

    @ApiModelProperty(position = 230)
    private Long createdBy;

    @ApiModelProperty(position = 240)
    private Long modified;

    @ApiModelProperty(position = 250)
    private Long modifiedBy;

}
