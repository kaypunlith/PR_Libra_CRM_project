package com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartOfAccountUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long parentId;

    @ApiModelProperty(position = 3)
    private Long chartAccountTypeId;

    @ApiModelProperty(position = 4)
    private Long chartAccountGroupId;

    @ApiModelProperty(position = 5)
    private String accountCodes;

    @ApiModelProperty(position = 6)
    private String accountDescription;

    @ApiModelProperty(position = 7)
    private String manual;


    @ApiModelProperty(position = 9, hidden = true)
    private String orcode;

    @ApiModelProperty(position = 10, hidden = true)
    private String pvcode;

    @ApiModelProperty(position = 11)
    private Long branchId;

    @ApiModelProperty(position = 12)
    private List<Long> companyId;



}
