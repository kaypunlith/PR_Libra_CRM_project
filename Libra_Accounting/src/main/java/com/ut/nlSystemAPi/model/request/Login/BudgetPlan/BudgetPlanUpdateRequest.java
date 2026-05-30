package com.ut.nlSystemAPi.model.request.Login.BudgetPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BudgetPlanUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private String year;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 15)
    private List<BudgetPlanDetailRequest> budgetPlanDetailRequests;

}
