package com.ut.nlSystemAPi.model.request.Login.Community;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommunitySlideRequest extends BaseModel {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2, hidden = true)
    private Long communityId;

    @ApiModelProperty(position = 3)
    private String slide;

    @ApiModelProperty(position = 4)
    private String thumbnailName;

}
