package com.ut.nlSystemAPi.model.entity.Lead;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadActivityCard extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long leadContactId;

    @ApiModelProperty(position = 3)
    private String position;

    @ApiModelProperty(position = 4)
    private Long actionStatusId;

    @ApiModelProperty(position = 5)
    private String issueDate;

    @ApiModelProperty(position = 6)
    private String subject;

    @ApiModelProperty(position = 7)
    private String resultAction;

    @ApiModelProperty(position = 8)
    private String other;
}
