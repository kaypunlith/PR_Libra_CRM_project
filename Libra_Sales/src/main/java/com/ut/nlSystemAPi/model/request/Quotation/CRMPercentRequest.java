package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CRMPercentRequest {

    @ApiModelProperty(position = 1)
    private Double red;

    @ApiModelProperty(position = 2)
    private Double yellow;

    @ApiModelProperty(position = 3)
    private Double green;

}
