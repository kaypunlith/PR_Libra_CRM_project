package com.ut.nlSystemAPi.model.request.Login.Activity;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivitySlideRequest extends BaseModel {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2, hidden = true)
    private Long activityId;

    @ApiModelProperty(position = 3)
    private String slide;

    @ApiModelProperty(position = 7)
    private String 	thumbnailName;
}
