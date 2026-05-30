package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferConsignmentFilter extends Filter {

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 6)
    private Long toWarehouseId;

    @ApiModelProperty(position = 6)
    private Long status;

    @ApiModelProperty(position = 7)
    private Long toType;

    @ApiModelProperty(position = 7)
    private Long viewByUser = 0L;

}
