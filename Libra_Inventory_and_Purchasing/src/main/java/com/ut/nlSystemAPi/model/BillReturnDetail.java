package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturnDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 1)
    private Long purchaseReturnId;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 4)
    private String expiredDate;

    @ApiModelProperty(position = 5)
    private Double qty;

    @ApiModelProperty(position = 5)
    private Long smallUom;

    @ApiModelProperty(position = 6)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private Long conversion;

    @ApiModelProperty(position = 7)
    private Double totalCost;

    @ApiModelProperty(position = 8)
    private Double unitCost;

}
