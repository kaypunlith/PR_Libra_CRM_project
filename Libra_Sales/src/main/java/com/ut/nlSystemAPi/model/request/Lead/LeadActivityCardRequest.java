package com.ut.nlSystemAPi.model.request.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadActivityCardRequest {

    @ApiModelProperty(position = 1)
    private Long leadContactId;

    @ApiModelProperty(position = 2)
    private String position;

    @ApiModelProperty(position = 3)
    private Long actionStatusId;

    @ApiModelProperty(position = 4)
    private String issueDate;

    @ApiModelProperty(position = 5)
    private String subject;

    @ApiModelProperty(position = 6)
    private String resultAction;

    @ApiModelProperty(position = 7)
    private String other;
}
