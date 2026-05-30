package com.ut.nlSystemAPi.model.response;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepositReportFilter extends Filter implements Serializable {

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private Long groupId;

    @ApiModelProperty(position = 7)
    private Long departmentId;

}
