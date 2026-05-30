package com.ut.nlSystemAPi.model.response.Taxation;

import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaxationResponse {

    @ApiModelProperty(position = 1)  
    private Long id;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Integer typeId;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 6)
    private Double percentage;

    @ApiModelProperty(position = 7)
    private Long chartOfAccountId;

    @ApiModelProperty(position = 7)
    private String chartOfAccount;

    @ApiModelProperty(position = 8)
    private Double rateForTax;

    @ApiModelProperty(position = 10)
    private Long branchId;

    @ApiModelProperty(position = 11)
    private String branchName;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private String createdDate;

    @ApiModelProperty(position = 14)
    private String modifiedBy;

    @ApiModelProperty(position = 15)
    private String modifiedDate;

    @ApiModelProperty(position = 16)
    private List<ModuleTypeResponse> moduleTypes;

}
