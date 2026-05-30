package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocationFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long warehouseId;

    @ApiModelProperty(position = 11)
    private Long productId;

}
