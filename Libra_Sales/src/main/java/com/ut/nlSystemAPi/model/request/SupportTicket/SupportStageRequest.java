package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportStageRequest {

    @ApiModelProperty(position = 1)
    private String name;
}
