package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProvidentFundDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Long providentFundId;

    @ApiModelProperty(position = 4)
    private Long totalOfMonth;

    @ApiModelProperty(position = 5)
    private Float bonusHaftProfundAmount;

    @ApiModelProperty(position = 6)
    private Float amountRequest;

}
