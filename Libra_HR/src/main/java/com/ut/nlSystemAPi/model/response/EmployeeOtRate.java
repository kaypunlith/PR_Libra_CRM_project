package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeOtRate implements Serializable {

    @ApiModelProperty(position = 1)
    private Float dayRate;

    @ApiModelProperty(position = 2)
    private Float nightRate;

    @ApiModelProperty(position = 3)
    private Float weekendRate;

    @ApiModelProperty(position = 4)
    private Float publicHolidayRate;

    public static EmployeeOtRate empty() {
        EmployeeOtRate rate = new EmployeeOtRate();
        rate.setDayRate(0F);
        rate.setNightRate(0F);
        rate.setWeekendRate(0F);
        rate.setPublicHolidayRate(0F);
        return rate;
    }
}
