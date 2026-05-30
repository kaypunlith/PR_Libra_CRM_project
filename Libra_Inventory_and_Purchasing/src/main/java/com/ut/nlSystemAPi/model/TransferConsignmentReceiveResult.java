package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignmentReceiveResult extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long transferOrderId;

    @ApiModelProperty(position = 3)
    private String trNumber;

    @ApiModelProperty(position = 5)
    private String trDate;

}
