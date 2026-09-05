package com.ut.nlSystemAPi.model.entity.Opportunities;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Opportunity extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String expectedDate;

    @ApiModelProperty(position = 5)
    private Long sourceId;

    @ApiModelProperty(position = 6)
    private Long customerId;

    @ApiModelProperty(position = 6)
    private Long leadId;


    @ApiModelProperty(position = 7)
    private Long employeeId;

    @ApiModelProperty(position = 8)
    private Long vendorId;

    @ApiModelProperty(position = 9)
    private Double amount;

    @ApiModelProperty(position = 10)
    private Long responsibilityId;

    @ApiModelProperty(position = 11)
    private Long divisionId;

    @ApiModelProperty(position = 12)
    private Long customerContactId;

    @ApiModelProperty(position = 13)
    private Long quotationId;

    @ApiModelProperty(position = 14)
    private Long salesOrderId;

    @ApiModelProperty(position = 15)
    private String description;
}
