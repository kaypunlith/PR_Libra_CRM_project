package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChartOfAccount extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long parentId;

  @ApiModelProperty(position = 3)
  private String parentCode;

  @ApiModelProperty(position = 4)
  private String parentDescription;

  @ApiModelProperty(position = 5)
  private Long chartAccountTypeId;

  @ApiModelProperty(position = 6)
  private String chartAccountTypeName;

  @ApiModelProperty(position = 7)
  private Long chartAccountGroupId;

  @ApiModelProperty(position = 8)
  private String chartAccountGroupName;

  @ApiModelProperty(position = 9)
  private String accountCodes;

  @ApiModelProperty(position = 10)
  private String accountDescription;

  @ApiModelProperty(position = 11)
  private String manual;

  @ApiModelProperty(position = 11)
  private Long branchId;

  @ApiModelProperty(position = 12)
  private String orcode;

  @ApiModelProperty(position = 13)
  private String pvcode;

  @ApiModelProperty(position = 13)
  private Long applyBranchType;

  @ApiModelProperty(position = 18)
  private List<CompanyChartAccountResponse> companyResponses;

}
