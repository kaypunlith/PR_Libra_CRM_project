package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturnMisc extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long purchaseReturnId;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 6)
    private Double qty;

    @ApiModelProperty(position = 7)
    private Long qtyUomId;

    @ApiModelProperty(position = 8)
    private String note;

    @ApiModelProperty(position = 9)
    private Double unitCost;

    @ApiModelProperty(position = 10)
    private Double totalCost;

}
