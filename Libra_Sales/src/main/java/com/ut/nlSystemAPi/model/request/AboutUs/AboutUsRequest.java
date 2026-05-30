package com.ut.nlSystemAPi.model.request.AboutUs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AboutUsRequest {

    @ApiModelProperty(position = 2)
    private String descriptionKh;

    @ApiModelProperty(position = 3)
    private String descriptionEn;
}
