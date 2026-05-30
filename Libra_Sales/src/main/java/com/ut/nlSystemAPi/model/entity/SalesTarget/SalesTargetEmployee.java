package com.ut.nlSystemAPi.model.entity.SalesTarget;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesTargetEmployee extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long saleTargetId;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Double organizationTargetAmountExisting;

    @ApiModelProperty(position = 4)
    private Double targetAmountExisting;

    @ApiModelProperty(position = 5)
    private Double targetAmountNew;

    @ApiModelProperty(position = 6)
    private Double organizationTargetAmountNew;

}