package com.ut.nlSystemAPi.model.entity.ActivityCard;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityCard extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long customerContactId;

    @ApiModelProperty(position = 3)
    private String position;

    @ApiModelProperty(position = 4)
    private Long actionStatusId;

    @ApiModelProperty(position = 5)
    private Long quotationId;

    @ApiModelProperty(position = 6)
    private String issueDate;

    @ApiModelProperty(position = 7)
    private Integer typeId;

    @ApiModelProperty(position = 8)
    private String subject;

    @ApiModelProperty(position = 9)
    private String resultAction;

    @ApiModelProperty(position = 10)
    private String other;
}
