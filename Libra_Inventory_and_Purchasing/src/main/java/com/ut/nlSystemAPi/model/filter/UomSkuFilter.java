package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UomSkuFilter{

    @ApiModelProperty(position = 10)
    private Long productId;

    @ApiModelProperty(position = 11)
    private Long uomId;

}
