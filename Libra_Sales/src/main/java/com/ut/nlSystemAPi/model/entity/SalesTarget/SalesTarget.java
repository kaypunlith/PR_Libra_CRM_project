package com.ut.nlSystemAPi.model.entity.SalesTarget;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SalesTarget extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String targetName;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long employeeId;

    @ApiModelProperty(position = 5)
    private Long currencyId;

    @ApiModelProperty(position = 7)
    private Double targetAmount;

    @ApiModelProperty(position = 8)
    private Double organizationTarget;

    @ApiModelProperty(position = 5)
    private Long indicatorFirst;

    @ApiModelProperty(position = 5)
    private Long indicatorSecond;

    @ApiModelProperty(position = 5)
    private Long indicatorThird;

    @ApiModelProperty(position = 5)
    private Long periodId;

}