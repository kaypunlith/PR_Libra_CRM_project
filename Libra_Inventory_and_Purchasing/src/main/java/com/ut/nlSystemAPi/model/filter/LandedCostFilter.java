package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LandedCostFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String date;

    @ApiModelProperty(position = 14)
    private Long status;

    @ApiModelProperty(position = 17)
    private Long vendorId;

    @ApiModelProperty(position = 17)
    private Long viewByUser = 0L;

}
