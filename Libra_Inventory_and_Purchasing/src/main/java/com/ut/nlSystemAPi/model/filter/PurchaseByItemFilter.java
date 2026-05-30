package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseByItemFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long view;

    @ApiModelProperty(position = 10)
    private Long type;

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long status;

    @ApiModelProperty(position = 14)
    private Long companyId;

    @ApiModelProperty(position = 15)
    private Long locationId;

    @ApiModelProperty(position = 16)
    private Long productId;

    @ApiModelProperty(position = 16)
    private Long productGroupId;

    @ApiModelProperty(position = 17)
    private Long createdBy;

}
