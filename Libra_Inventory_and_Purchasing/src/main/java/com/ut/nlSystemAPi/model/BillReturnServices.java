package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturnServices extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long purchaseReturnId;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 4)
    private Double qty;

    @ApiModelProperty(position = 6)
    private Double totalCost;

    @ApiModelProperty(position = 7)
    private Double unitCost;

}
