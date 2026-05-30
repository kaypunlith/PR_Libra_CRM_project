package com.ut.nlSystemAPi.model.response.Activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivitySlideResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long activityId;

    @ApiModelProperty(position = 3)
    private String slide;

    @ApiModelProperty(position = 7)
    private String 	thumbnailName;

    @ApiModelProperty(position = 4)
    private String createdBy;

    @ApiModelProperty(position = 5)
    private String createdDate;

    @ApiModelProperty(position = 6)
    private String modifiedBy;

    @ApiModelProperty(position = 7)
    private String modifiedDate;

}
