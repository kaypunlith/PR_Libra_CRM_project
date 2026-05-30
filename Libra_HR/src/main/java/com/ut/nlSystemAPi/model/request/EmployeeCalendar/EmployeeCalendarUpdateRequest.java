package com.ut.nlSystemAPi.model.request.EmployeeCalendar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeCalendarUpdateRequest extends EmployeeCalendarRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
