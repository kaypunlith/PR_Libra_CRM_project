package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodReceiptNoteFilter extends Filter {

    @ApiModelProperty(position = 11)
    private Long warehouseId ;

    @ApiModelProperty(position = 12)
    private Long locationId;

    @ApiModelProperty(position = 13)
    private Long status;

}
