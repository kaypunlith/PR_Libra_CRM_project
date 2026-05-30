package com.ut.nlSystemAPi.model.response.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MakeDepositApplyToResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 3)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

}
