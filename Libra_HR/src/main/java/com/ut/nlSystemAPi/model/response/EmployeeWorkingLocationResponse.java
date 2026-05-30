package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeWorkingLocationResponse {

    @ApiModelProperty(position = 1)
    private Long locationId;

    @ApiModelProperty(position = 2)
    private String locationName;

   
}
