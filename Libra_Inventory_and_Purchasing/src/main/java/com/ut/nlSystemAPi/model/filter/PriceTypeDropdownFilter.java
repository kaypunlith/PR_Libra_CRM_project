package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PriceTypeDropdownFilter extends Filter {

    @ApiModelProperty(position = 13)
    private Long companyId;

    @ApiModelProperty(position = 13)
    private Long organizationId;

}
