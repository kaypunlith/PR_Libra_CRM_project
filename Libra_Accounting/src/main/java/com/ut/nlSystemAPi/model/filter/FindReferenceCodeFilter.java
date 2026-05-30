package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FindReferenceCodeFilter {

    @ApiModelProperty(position =12)
    private Long moduleId;

    @ApiModelProperty(position =12)
    private Long branchId;

}
