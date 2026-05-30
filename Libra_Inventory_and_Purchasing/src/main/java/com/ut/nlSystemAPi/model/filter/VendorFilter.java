package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VendorFilter extends Filter {

    @ApiModelProperty(position = 13)
    private String dateFrom;

    @ApiModelProperty(position = 14)
    private String dateTo;

    @ApiModelProperty(position = 15)
    private Long type;

    @ApiModelProperty(position = 15)
    private Long isAp;

}
