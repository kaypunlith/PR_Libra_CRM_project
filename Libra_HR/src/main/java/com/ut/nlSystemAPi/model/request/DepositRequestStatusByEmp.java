package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositRequestStatusByEmp {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeesId;

    @ApiModelProperty(position = 3)
    private Long status;

    @ApiModelProperty(position = 104, hidden = true)
    private Long modifiedBy;

}
