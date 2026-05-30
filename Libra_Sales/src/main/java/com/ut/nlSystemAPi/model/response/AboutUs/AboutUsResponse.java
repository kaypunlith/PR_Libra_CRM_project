package com.ut.nlSystemAPi.model.response.AboutUs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AboutUsResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String descriptionKh;

    @ApiModelProperty(position = 3)
    private String descriptionEn;

    @ApiModelProperty(position = 4)
    private String created;

    @ApiModelProperty(position = 5)
    private String createdBy;

    @ApiModelProperty(position = 6)
    private String modified;

    @ApiModelProperty(position = 7)
    private String modifiedBy;
}
