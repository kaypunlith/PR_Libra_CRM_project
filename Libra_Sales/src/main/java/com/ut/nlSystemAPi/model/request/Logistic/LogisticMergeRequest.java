package com.ut.nlSystemAPi.model.request.Logistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogisticMergeRequest {

    @ApiModelProperty(position = 1)
    private Integer operationType;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long organizationGroupId;

    @ApiModelProperty(position = 4)
    private Long organizationId;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private List<LogisticMergeDetailRequest> details;

}