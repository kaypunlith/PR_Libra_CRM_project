package com.ut.nlSystemAPi.model.response.ChartAccountGroup;

import com.ut.nlSystemAPi.model.ChartAccountType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountGroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long accountTypeId;

    @ApiModelProperty(position = 4)
    private String accountTypeName;

    @ApiModelProperty(position = 4)
    private Integer status;

    @ApiModelProperty(position = 3)
    private Long expense;

    @ApiModelProperty(position = 5)
    private String createdBy;

    @ApiModelProperty(position = 6)
    private String createdDate;

    @ApiModelProperty(position = 7)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private String modifiedDate;
}
