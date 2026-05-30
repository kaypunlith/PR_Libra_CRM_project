package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PgroupChartAcount extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pgroupId;

    @ApiModelProperty(position = 3)
    private Long accountTypeId;

    @ApiModelProperty(position = 4)
    private Long chartAccountId;
}
