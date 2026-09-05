package com.ut.nlSystemAPi.model.request.CrmCategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrmCategoryUpdateRequest extends CrmCategoryRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
