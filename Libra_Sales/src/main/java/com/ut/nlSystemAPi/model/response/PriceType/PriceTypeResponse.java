package com.ut.nlSystemAPi.model.response.PriceType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceTypeResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 4)
    private String applyTo;

    @ApiModelProperty(position = 4)
    private Long isShowCatalogue;

    @ApiModelProperty(position = 5)
    private Long ordering;

}
