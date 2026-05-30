package com.ut.nlSystemAPi.model.request.Login.Taxation;

import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaxationRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Integer typeId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Double percentage;

    @ApiModelProperty(position = 5)
    private Long chartOfAccountId;

    @ApiModelProperty(position = 7)
    private Double rateForTax;

    @ApiModelProperty(position = 8)
    private Long branchId;

    @ApiModelProperty(position = 15)
    private List<Long> moduleTypeId;
}
