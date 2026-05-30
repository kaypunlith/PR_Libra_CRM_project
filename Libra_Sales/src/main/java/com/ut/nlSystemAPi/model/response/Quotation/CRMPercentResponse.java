package com.ut.nlSystemAPi.model.response.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CRMPercentResponse {

    @ApiModelProperty(position = 1)
    private Double red;

    @ApiModelProperty(position = 2)
    private Double yellow;

    @ApiModelProperty(position = 3)
    private Double green;

}
