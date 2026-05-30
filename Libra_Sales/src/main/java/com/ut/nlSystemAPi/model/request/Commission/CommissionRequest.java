package com.ut.nlSystemAPi.model.request.Commission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommissionRequest {

    @ApiModelProperty(position = 2)
    private Long warehouseId;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 5)
    private String startDate;

    @ApiModelProperty(position = 6)
    private String endDate;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 8)
    private Integer type;

    @ApiModelProperty(position = 8)
    private Integer recurring;

    @ApiModelProperty(position = 9)
    private Double target;

    @ApiModelProperty(position = 10)
    private Double amount;

    @ApiModelProperty(position = 11)
    private Double percent;

    @ApiModelProperty(position = 12)
    private List<CommissionDetailRequest> details;

    @ApiModelProperty(position = 13)
    private List<Long> employees;
}

