package com.ut.nlSystemAPi.model.response.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignmentLocationResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private Double stockQty;
}
