package com.ut.nlSystemAPi.model.response.Banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BannerResponse {

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
    private String promotionName;

    @ApiModelProperty(position = 10)
    private Long productId;

    @ApiModelProperty(position = 11)
    private String productName;

    @ApiModelProperty(position = 12)
    private Integer isActive;

    @ApiModelProperty(position = 13)
    private String created;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 15)
    private String modified;

    @ApiModelProperty(position = 16)
    private String modifiedBy;

}
