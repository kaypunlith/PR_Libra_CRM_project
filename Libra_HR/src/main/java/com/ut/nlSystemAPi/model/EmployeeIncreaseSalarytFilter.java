package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeIncreaseSalarytFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 6)
    private Long employeesId;

    @ApiModelProperty(position = 7)
    private String date;

}
