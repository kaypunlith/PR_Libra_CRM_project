package com.ut.nlSystemAPi.model.request.ActivityCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityCardRequest {

    @ApiModelProperty(position = 1)
    private Long customerContactId;

    @ApiModelProperty(position = 2)
    private String position;

    @ApiModelProperty(position = 3)
    private Long actionStatusId;

    @ApiModelProperty(position = 4)
    private Long quotationId;

    @ApiModelProperty(position = 5)
    private String issueDate;

    @ApiModelProperty(position = 6)
    private Integer typeId;

    @ApiModelProperty(position = 7)
    private String subject;

    @ApiModelProperty(position = 8)
    private String resultAction;

    @ApiModelProperty(position = 9)
    private String other;
}
