package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityReferenceResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long customerId;

    @ApiModelProperty(position = 4)
    private Long customerContactId;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 6)
    private Double discount;

    @ApiModelProperty(position = 7)
    private Double totalVat;
}
