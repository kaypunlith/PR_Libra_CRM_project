package com.ut.nlSystemAPi.model.response.BudgetPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BudgetPlanResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private String branchName;

    @ApiModelProperty(position = 4)
    private String year;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 6)
    private String description;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 9)
    private String createdDate;

    @ApiModelProperty(position = 10)
    private String modifiedDate;

    @ApiModelProperty(position = 15)
    private List<BudgetPlanTableResponse> budgetPlanTableResponses;

}
