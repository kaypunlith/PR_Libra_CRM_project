package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryResponse {

    @ApiModelProperty
    private Long categoryId;

    @ApiModelProperty
    private String categoryName;

}
