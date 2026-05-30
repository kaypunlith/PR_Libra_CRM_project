package com.ut.nlSystemAPi.model.entity.BOM;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BOM extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long boqId;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private Long organizationId;

    @ApiModelProperty(position = 9)
    private Long organizationContactId;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 12)
    private Double totalCost;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

}
