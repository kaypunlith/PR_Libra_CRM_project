package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpenseRequestDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long pvRequestId;

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 4)
    private String note;

    @ApiModelProperty(position = 5)
    private Long qty;

    @ApiModelProperty(position = 6)
    private Long qtyUomId;

    @ApiModelProperty(position = 7)
    private Double unitCost ;

    @ApiModelProperty(position = 8)
    private Double totalCost;

    @ApiModelProperty(position = 8)
    private Long conversion;

    @ApiModelProperty(position = 9)
    private Long isClose;

}
