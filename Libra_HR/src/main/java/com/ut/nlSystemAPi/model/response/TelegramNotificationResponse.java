package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TelegramNotificationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 10)
    private String name;

    @ApiModelProperty(position = 20)
    private String chatId;

    @ApiModelProperty(position = 220)
    private String created;

    @ApiModelProperty(position = 230)
    private String createdBy;

    @ApiModelProperty(position = 240)
    private String modified;

    @ApiModelProperty(position = 250)
    private String modifiedBy;

    @ApiModelProperty(position = 260)
    private List<DropdownResponse> departments;

}
