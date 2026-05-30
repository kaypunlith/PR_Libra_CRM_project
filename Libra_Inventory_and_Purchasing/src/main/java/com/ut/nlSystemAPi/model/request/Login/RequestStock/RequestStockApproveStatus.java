package com.ut.nlSystemAPi.model.request.Login.RequestStock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestStockApproveStatus {
    @ApiModelProperty(position =1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long status;

    @ApiModelProperty(position = 3)
    private Long approvedBy;
}
