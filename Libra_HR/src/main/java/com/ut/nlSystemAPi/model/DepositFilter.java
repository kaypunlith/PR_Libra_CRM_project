package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepositFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 4)
    private String dateFrom;

    @ApiModelProperty(position = 5)
    private String dateTo;

    @ApiModelProperty(position = 6)
    private Long titleId;

    @ApiModelProperty(position = 7)
    private Long status;

}
