package com.ut.nlSystemAPi.model.entity.SaleInvoice;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationLimitSetting extends BaseModel {

    @ApiModelProperty(position = 12)
    private Double limitBalance;

    @ApiModelProperty(position = 12)
    private Long limitInvoice;

    @ApiModelProperty(position = 12)
    private Long currentInvoice;

    @ApiModelProperty(position = 12)
    private Double currentBalance;


}
