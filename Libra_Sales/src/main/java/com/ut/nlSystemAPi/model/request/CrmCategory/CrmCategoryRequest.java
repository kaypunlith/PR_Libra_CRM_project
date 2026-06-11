package com.ut.nlSystemAPi.model.request.CrmCategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrmCategoryRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String photo;
}
