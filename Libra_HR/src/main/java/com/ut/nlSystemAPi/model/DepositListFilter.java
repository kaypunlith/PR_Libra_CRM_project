package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepositListFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 4)
    private String filterMonth;

    @ApiModelProperty(position = 5)
    private Long employeeId;

}
