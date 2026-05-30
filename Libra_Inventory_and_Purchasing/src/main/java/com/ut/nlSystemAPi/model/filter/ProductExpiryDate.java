package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductExpiryDate extends Filter {

    @ApiModelProperty(position = 6)
    private Long companyId;

    @ApiModelProperty(position = 7)
    private Long productGroupId;

    @ApiModelProperty(position = 8)
    private Long createdBy;
}
