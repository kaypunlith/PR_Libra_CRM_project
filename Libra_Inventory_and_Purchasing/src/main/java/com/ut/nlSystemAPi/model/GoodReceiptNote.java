package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodReceiptNote extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long Id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long locationGroupId;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 5)
    private String code;

    @ApiModelProperty(position = 6)
    private Long vendorId;

    @ApiModelProperty(position = 7)
    private Long purchaseRequestId;

    @ApiModelProperty(position = 8)
    private String date;

    @ApiModelProperty(position = 9)
    private String note;

    @ApiModelProperty(position = 10)
    private Long approvedBy;

    @ApiModelProperty(position =11)
    private Long approved;






}
