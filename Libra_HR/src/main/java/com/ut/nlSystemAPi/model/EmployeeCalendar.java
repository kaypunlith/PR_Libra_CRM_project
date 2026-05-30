package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeCalendar extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 4)
    private Integer isRepeatable;

    @ApiModelProperty(position = 5)
    private Integer repeatType;

    @ApiModelProperty(position = 6)
    private List<String> repeatDays;

    @ApiModelProperty(position = 7)
    private List<Long> marketIds;

    @ApiModelProperty(position = 8)
    private String description;

    @ApiModelProperty(position = 9)
    private String dateFrom;

    @ApiModelProperty(position = 10)
    private String dateTo;
}
