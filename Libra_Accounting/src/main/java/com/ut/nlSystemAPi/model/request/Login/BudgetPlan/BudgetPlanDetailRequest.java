package com.ut.nlSystemAPi.model.request.Login.BudgetPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BudgetPlanDetailRequest {

    @ApiModelProperty(position = 1, hidden = true)
    private Long budgetPlsId;

    @ApiModelProperty(position = 2)
    private Long chartAccountId;

    @ApiModelProperty(position = 3)
    private Double m1;

    @ApiModelProperty(position = 4)
    private Double m2;

    @ApiModelProperty(position = 5)
    private Double m3;

    @ApiModelProperty(position = 6)
    private Double m4;

    @ApiModelProperty(position = 7)
    private Double m5;

    @ApiModelProperty(position = 8)
    private Double m6;

    @ApiModelProperty(position = 9)
    private Double m7;

    @ApiModelProperty(position = 10)
    private Double m8;

    @ApiModelProperty(position = 12)
    private Double m9;

    @ApiModelProperty(position = 13)
    private Double m10;

    @ApiModelProperty(position = 14)
    private Double m11;

    @ApiModelProperty(position = 15)
    private Double m12;

}
