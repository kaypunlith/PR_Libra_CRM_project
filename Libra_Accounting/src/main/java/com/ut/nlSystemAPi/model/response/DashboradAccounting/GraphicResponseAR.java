package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GraphicResponseAR {
    @ApiModelProperty(position = 1)
    private String saleOrderId;

    @ApiModelProperty(position = 1)
    private String arrGlId;

    @ApiModelProperty(position = 1)
    private String arrMainGlId;

    @ApiModelProperty(position = 1)
    private String creditMemo;


    @ApiModelProperty(position = 1)
    private Double amount;
}
