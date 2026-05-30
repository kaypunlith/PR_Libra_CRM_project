package com.ut.nlSystemAPi.model.request.Login.ChartAccountType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountTypeRequest {

    @ApiModelProperty(position = 1)
    private String name;

}
