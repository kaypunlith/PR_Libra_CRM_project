package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPhotoResponse {

    @ApiModelProperty(position = 1)
    private String url;

    @ApiModelProperty(position = 2)
    private String name;

}
