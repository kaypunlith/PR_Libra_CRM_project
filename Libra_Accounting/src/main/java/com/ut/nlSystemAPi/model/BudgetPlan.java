package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BudgetPlan extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;
  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long companyId;

  @ApiModelProperty(position = 2)
  private Long branchId;

  @ApiModelProperty(position = 3)
  private String companyName;

  @ApiModelProperty(position = 4)
  private String year;

  @ApiModelProperty(position = 5)
  private String name;

  @ApiModelProperty(position = 6)
  private String description;

  @ApiModelProperty(position = 11)
  private List<BudgetPlanDetail> budgetPlanDetails;

}
