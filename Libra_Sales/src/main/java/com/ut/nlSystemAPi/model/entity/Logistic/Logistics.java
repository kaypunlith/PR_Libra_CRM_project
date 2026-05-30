package com.ut.nlSystemAPi.model.entity.Logistic;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Logistics extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long organizationGroupId;

    @ApiModelProperty(position = 4)
    private Long organizationId;

    @ApiModelProperty(position = 5)
    private String date;

}