package com.ut.nlSystemAPi.model;

import java.io.Serializable;

import com.ut.nlSystemAPi.model.base.Filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProvidentFundListFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 4)
    private String filterMonth;

    @ApiModelProperty(position = 5)
    private Long employeeId;

}
