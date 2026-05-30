package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Taxation extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private Integer typeId;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 6)
    private Double percentage;

    @ApiModelProperty(position = 7)
    private Long chartOfAccountId;

    @ApiModelProperty(position = 8)
    private String chartOfAccountName;

    @ApiModelProperty(position = 9)
    private Double rateForTax;

    @ApiModelProperty(position = 10)
    private Long branchId;


    @ApiModelProperty(position = 11)
    private List<ModuleTypeResponse> moduleTypes;

}
