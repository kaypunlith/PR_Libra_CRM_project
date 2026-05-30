package com.ut.nlSystemAPi.model.entity.Banner;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Banner extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String imageUrl;

    @ApiModelProperty(position = 3)
    private String imageName;

    @ApiModelProperty(position = 4)
    private String mainDescription;

    @ApiModelProperty(position = 5)
    private String subDescription;

    @ApiModelProperty(position = 6)
    private String mainDescriptionKh;

    @ApiModelProperty(position = 7)
    private String subDescriptionKh;

    @ApiModelProperty(position = 8)
    private Long promotionId;

    @ApiModelProperty(position = 9)
    private Long productId;

}
