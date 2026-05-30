package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturnWithPbsReceipt extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long billReturnId;

    @ApiModelProperty(position = 3)
    private Long purchaseBillId;

    @ApiModelProperty(position = 4)
    private Double totalCost;

    @ApiModelProperty(position = 5)
    private String applyDate;


}
