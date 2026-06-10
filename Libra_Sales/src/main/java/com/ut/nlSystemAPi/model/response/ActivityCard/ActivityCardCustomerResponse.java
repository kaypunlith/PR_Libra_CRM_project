package com.ut.nlSystemAPi.model.response.ActivityCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityCardCustomerResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String customerGroupName;

    @ApiModelProperty(position = 3)
    private String customerCode;

    @ApiModelProperty(position = 4)
    private String customerName;

    @ApiModelProperty(position = 5)
    private String telephone;

    @ApiModelProperty(position = 6)
    private String paymentTermName;

    @ApiModelProperty(position = 7)
    private String contactName;

    @ApiModelProperty(position = 8)
    private Integer customerTypeId;
}
