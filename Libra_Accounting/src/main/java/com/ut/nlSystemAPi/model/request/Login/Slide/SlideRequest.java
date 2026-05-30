package com.ut.nlSystemAPi.model.request.Login.Slide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SlideRequest {
    @ApiModelProperty(position = 2)
    private Long templeId;

    @ApiModelProperty(position = 3)
    private String nameKh;

    @ApiModelProperty(position = 4)
    private String nameEn;

    @ApiModelProperty(position = 5)
    private String nameCn;

    @ApiModelProperty(position = 6)
    private String thumbnail;

    @ApiModelProperty(position = 4)
    private String thumbnailName;

    @ApiModelProperty(position = 6)
    private Long ordering;

}
