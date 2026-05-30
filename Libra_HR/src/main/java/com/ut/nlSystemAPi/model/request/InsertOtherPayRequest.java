package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class InsertOtherPayRequest extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long branchId;

    @ApiModelProperty(position = 1)
    private Long bonusId;

    @ApiModelProperty(position = 1)
    private Float amount;
}
