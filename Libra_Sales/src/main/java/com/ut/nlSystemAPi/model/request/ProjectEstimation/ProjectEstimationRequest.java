package com.ut.nlSystemAPi.model.request.ProjectEstimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectEstimationRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private Long customerContactId;

    @ApiModelProperty(position = 6)
    private Long projectLeaderId;

    @ApiModelProperty(position = 7)
    private Long boqId;

    @ApiModelProperty(position = 8)
    private String date;

    @ApiModelProperty(position = 9)
    private Long duration;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Double totalEstimateCost;

    @ApiModelProperty(position = 12)
    private Double grossProfitAmount;

    @ApiModelProperty(position = 13)
    private Double grossProfitPercent;

    @ApiModelProperty(position = 14)
    private List<ProjectEstimationDetailRequest> details;

}