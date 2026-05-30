package com.ut.nlSystemAPi.model.response.ProductBrand;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductBrandResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String created;

    @ApiModelProperty(position = 4)
    private String createdBy;

    @ApiModelProperty(position = 5)
    private String modified;

    @ApiModelProperty(position = 6)
    private String modifiedBy;

    @ApiModelProperty(position = 7)
    private Integer isActive;
}
