package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GraphicResponseAP {
    @ApiModelProperty(position = 1)
    private String purchaseOrderId;

    @ApiModelProperty(position = 1)
    private String arrGlId;

    @ApiModelProperty(position = 1)
    private String arrMainGlId;

    @ApiModelProperty(position = 1)
    private String purchaseReturnId;

    @ApiModelProperty(position = 1)
    private Double amount;
}
