package com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountGroupUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long accountTypeId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Integer expense;

}
