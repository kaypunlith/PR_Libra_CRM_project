package com.ut.nlSystemAPi.model.request.Login.Taxation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaxationUpdateRequest {

    @ApiModelProperty(position = 1)  
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Integer typeId;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private Double percentage;

    @ApiModelProperty(position = 6)
    private Long chartOfAccountId;

    @ApiModelProperty(position = 7)
    private Double rateForTax;

    @ApiModelProperty(position = 10)
    private Long branchId;


    @ApiModelProperty(position = 15)
    private List<Long> moduleTypeId;

}
