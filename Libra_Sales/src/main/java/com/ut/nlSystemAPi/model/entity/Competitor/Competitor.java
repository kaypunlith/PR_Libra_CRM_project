package com.ut.nlSystemAPi.model.entity.Competitor;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Competitor extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long priorityId;

    @ApiModelProperty(position = 3)
    private Long statusId;

    @ApiModelProperty(position = 4)
    private Long stageId;

    @ApiModelProperty(position = 5)
    private Long employeeAmountId;

    @ApiModelProperty(position = 6)
    private String partner;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 6)
    private String address;

    @ApiModelProperty(position = 6)
    private String description;

    @ApiModelProperty(position = 6)
    private String contact;

    @ApiModelProperty(position = 6)
    private String employeeInfo;

    @ApiModelProperty(position = 6)
    private Double numberOfCustomer;

    @ApiModelProperty(position = 6)
    private Double salesRevenue;

}