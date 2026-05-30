package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferScheduleDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long transferScheduleId;

    @ApiModelProperty(position = 2)
    private Long pvRequestId;


}
