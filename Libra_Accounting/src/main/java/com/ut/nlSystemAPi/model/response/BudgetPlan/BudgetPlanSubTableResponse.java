package com.ut.nlSystemAPi.model.response.BudgetPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BudgetPlanSubTableResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 15)
    private List<BudgetPlanSubSubTableResponse> subSubTableResponses;

}
