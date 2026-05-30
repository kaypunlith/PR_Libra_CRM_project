package com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartOfAccountRequest {

    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 2)
    private Long chartAccountTypeId;

    @ApiModelProperty(position = 3)
    private Long chartAccountGroupId;

    @ApiModelProperty(position = 4)
    private String accountCodes;

    @ApiModelProperty(position = 4)
    private Long applyBranchType;

    @ApiModelProperty(position = 5)
    private String accountDescription;

    @ApiModelProperty(position = 6)
    private String manual;

    @ApiModelProperty(position = 7, hidden = true)
    private String orcode;

    @ApiModelProperty(position = 8, hidden = true)
    private String pvcode;

    @ApiModelProperty(position = 10)
    private Long branchId;

    @ApiModelProperty(position = 11)
    private List<Long> companyId;



}
