package com.ut.nlSystemAPi.model.response.ProjectEstimation;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
public class ProjectEstimationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String pcCode;

    @ApiModelProperty(position = 4)
    private Long organizationOwnerId;

    @ApiModelProperty(position = 5)
    private String organizationOwnerName;

    @ApiModelProperty(position = 6)
    private Long personInChargeId;

    @ApiModelProperty(position = 7)
    private String personInChargeName;

    @ApiModelProperty(position = 8)
    private Long projectLeaderId;

    @ApiModelProperty(position = 9)
    private String projectLeaderName;

    @ApiModelProperty(position = 10)
    private Long boqId;

    @ApiModelProperty(position = 11)
    private String boqCode;

    @ApiModelProperty(position = 12)
    private String date;

    @ApiModelProperty(position = 13)
    private Long duration;

    @ApiModelProperty(position = 14)
    private String note;

    @ApiModelProperty(position = 15)
    private Double totalProjectRevenue;

    @ApiModelProperty(position = 16)
    private Double totalEstimateCost;

    @ApiModelProperty(position = 17)
    private Double grossProfitAmount;

    @ApiModelProperty(position = 18)
    private Double grossProfitPercent;

    @ApiModelProperty(position = 18)
    private String grossProfitColor;

    @ApiModelProperty(position = 19)
    private Double roi;

    @ApiModelProperty(position = 20)
    private Integer status;

    @ApiModelProperty(position = 21)
    private String created;

    @ApiModelProperty(position = 22)
    private String createdBy;

    @ApiModelProperty(position = 23)
    private String modified;

    @ApiModelProperty(position = 24)
    private String modifiedBy;

    @ApiModelProperty(position = 25)
    private List<ProjectEstimationDetailResponse> details;
}