package com.ut.nlSystemAPi.model.request.Login.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PvRequestDetailsRequest {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private Double unitCost;

    @ApiModelProperty(position = 7)
    private Double totalCost;

    @ApiModelProperty(position = 7)
    private Long conversion;

}
