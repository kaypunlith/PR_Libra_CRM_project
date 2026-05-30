package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollLock extends BaseModel implements Serializable {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long departmentId;

    @ApiModelProperty(position = 3)
    private String payDate;

    @ApiModelProperty(position = 4)
    private Boolean isLocked;
}
