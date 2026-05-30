package com.ut.nlSystemAPi.model.entity.AboutUs;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AboutUs extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String descriptionKh;

    @ApiModelProperty(position = 3)
    private String descriptionEn;
}
