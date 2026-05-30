package com.ut.nlSystemAPi.model.response.Competitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CompetitorResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long priorityId;

    @ApiModelProperty(position = 3)
    private String priorityName;

    @ApiModelProperty(position = 4)
    private Long statusId;

    @ApiModelProperty(position = 5)
    private String statusName;

    @ApiModelProperty(position = 6)
    private Long stageId;

    @ApiModelProperty(position = 7)
    private String stageName;

    @ApiModelProperty(position = 8)
    private Long employeeAmountId;

    @ApiModelProperty(position = 9)
    private String employeeAmountName;

    @ApiModelProperty(position = 10)
    private String name;

    @ApiModelProperty(position = 10)
    private String partner;

    @ApiModelProperty(position = 11)
    private String address;

    @ApiModelProperty(position = 12)
    private String description;

    @ApiModelProperty(position = 12)
    private String contact;

    @ApiModelProperty(position = 12)
    private String employeeInfo;

    @ApiModelProperty(position = 13)
    private Double numberOfCustomer;

    @ApiModelProperty(position = 14)
    private Double estimateSalesRevenue;

    @ApiModelProperty(position = 15)
    private Integer isClose;

    @ApiModelProperty(position = 16)
    private String businessTypes;

    @ApiModelProperty(position = 17)
    private String mainProducts;

    @ApiModelProperty(position = 18)
    private String productTypes;

    @ApiModelProperty(position = 19)
    private List<CompetitorDetailResponse> businessType;

    @ApiModelProperty(position = 20)
    private List<CompetitorDetailResponse> mainProduct;

    @ApiModelProperty(position = 21)
    private List<CompetitorDetailResponse> productType;

    @ApiModelProperty(position = 22)
    private List<CompetitorDetailResponse> weakness;

    @ApiModelProperty(position = 23)
    private List<CompetitorDetailResponse> strength;
}