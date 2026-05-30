package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeedbackReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long userMobileId;

    @ApiModelProperty(position = 3)
    private String userMobileName;

    @ApiModelProperty(position = 4)
    private Long templeId;

    @ApiModelProperty(position = 5)
    private String templeName;

    @ApiModelProperty(position = 6)
    private Long typeOfFeedbackId;

    @ApiModelProperty(position = 7)
    private String typeOfFeedbackName;

    @ApiModelProperty(position = 8)
    private String description;

    @ApiModelProperty(position = 8)
    private String createdDate;

}
