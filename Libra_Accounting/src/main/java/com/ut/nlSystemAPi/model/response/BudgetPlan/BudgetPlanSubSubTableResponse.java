package com.ut.nlSystemAPi.model.response.BudgetPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BudgetPlanSubSubTableResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 15)
    private BudgetPlanDetailResponse budgetPlanDetailResponses;

}
