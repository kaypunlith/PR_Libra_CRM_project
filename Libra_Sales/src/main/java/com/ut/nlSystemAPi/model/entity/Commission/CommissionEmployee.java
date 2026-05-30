package com.ut.nlSystemAPi.model.entity.Commission;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionEmployee extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long commissionId;

    @ApiModelProperty(position = 3)
    private Long employeeId;
}

