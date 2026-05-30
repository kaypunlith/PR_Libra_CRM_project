package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeWorkshiftResponse {

    @ApiModelProperty(position = 1)
    private Long workShiftId;

    @ApiModelProperty(position = 2)
    private String workShiftName;

   
}
