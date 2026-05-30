package com.ut.nlSystemAPi.model.response;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyEmployeesFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 4)
    private Long employeeId;

}
