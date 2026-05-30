package com.ut.nlSystemAPi.model.response.ChartAccountType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountTypeResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Integer status;

    @ApiModelProperty(position = 4)
    private String createdBy;

    @ApiModelProperty(position = 5)
    private String createdDate;

    @ApiModelProperty(position = 5)
    private String modifiedBy;

    @ApiModelProperty(position = 6)
    private String modifiedDate;
}
