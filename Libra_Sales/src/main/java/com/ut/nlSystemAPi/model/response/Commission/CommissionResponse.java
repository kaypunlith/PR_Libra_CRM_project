package com.ut.nlSystemAPi.model.response.Commission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommissionResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long warehouseId;

    @ApiModelProperty(position = 3)
    private String warehouseName;

    @ApiModelProperty(position = 4)
    private String code;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 6)
    private String startDate;

    @ApiModelProperty(position = 7)
    private String endDate;

    @ApiModelProperty(position = 8)
    private String note;

    @ApiModelProperty(position = 9)
    private Integer type;

    @ApiModelProperty(position = 9)
    private Integer recurring;

    @ApiModelProperty(position = 10)
    private Double target;

    @ApiModelProperty(position = 11)
    private Double amount;

    @ApiModelProperty(position = 12)
    private Double percent;

    @ApiModelProperty(position = 13)
    private Integer status;

    @ApiModelProperty(position = 14)
    private String created;

    @ApiModelProperty(position = 15)
    private String createdBy;

    @ApiModelProperty(position = 16)
    private String modified;

    @ApiModelProperty(position = 17)
    private String modifiedBy;

    @ApiModelProperty(position = 18)
    private List<CommissionDetailResponse> details;

    @ApiModelProperty(position = 19)
    private List<CommissionEmployeeResponse> employees;
}

