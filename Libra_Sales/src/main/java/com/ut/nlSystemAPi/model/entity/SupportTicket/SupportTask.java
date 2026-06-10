package com.ut.nlSystemAPi.model.entity.SupportTicket;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTask extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long activityId;

    @ApiModelProperty(position = 3)
    private String name;
}
