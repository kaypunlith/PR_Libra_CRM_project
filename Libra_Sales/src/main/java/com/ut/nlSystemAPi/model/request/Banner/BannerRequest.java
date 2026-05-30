package com.ut.nlSystemAPi.model.request.Banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BannerRequest {

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
