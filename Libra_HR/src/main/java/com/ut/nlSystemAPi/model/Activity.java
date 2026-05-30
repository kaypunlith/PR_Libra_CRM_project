package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Activity extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String nameKh;

    @ApiModelProperty(position = 3)
    private String nameEn;

    @ApiModelProperty(position = 4)
    private String nameCn;

    @ApiModelProperty(position = 5)
    private String lats;

    @ApiModelProperty(position = 6)
    private String longs;

    @ApiModelProperty(position = 7)
    private String 	thumbnail;

    @ApiModelProperty(position = 7)
    private String 	thumbnailName;

    @ApiModelProperty(position =8)
    private String 	descriptionKh;

    @ApiModelProperty(position = 9)
    private String 	descriptionEn;

    @ApiModelProperty(position = 10)
    private String 	descriptionCn;

}
