package com.ut.nlSystemAPi.model.response.ChartOfAccount;

import com.ut.nlSystemAPi.model.response.Dropdown.BranchChartAccountResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartOfAccountResponse {

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

    @ApiModelProperty(position = 12)
    private String orcode;

    @ApiModelProperty(position = 13)
    private Long branchId;

    @ApiModelProperty(position = 14)
    private String branchName;

    @ApiModelProperty(position = 15)
    private String pvcode;

    @ApiModelProperty(position = 16)
    private Double balance;


    @ApiModelProperty(position = 17)
    private String createdDate;

    @ApiModelProperty(position = 18)
    private String modifiedDate;

    @ApiModelProperty(position = 19)
    private String createdBy;

    @ApiModelProperty(position = 20)
    private String modifiedBy;

    @ApiModelProperty(position = 21)
    private Long status;

    @ApiModelProperty(position = 21)
    private Long spacing;

    @ApiModelProperty(position = 22)
    private List<CompanyChartAccountResponse> companyResponses;

    @ApiModelProperty(position = 23)
    private List<ChartOfAccountResponse> subAccount;
}
