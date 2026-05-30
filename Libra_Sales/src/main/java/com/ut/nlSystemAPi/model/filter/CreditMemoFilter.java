package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class CreditMemoFilter extends Filter {

    @ApiModelProperty(position = 12)
    private Long status;

    @ApiModelProperty(position = 13)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private String dateFrom;

    @ApiModelProperty(position = 14)
    private String dateTo;

    @ApiModelProperty(position = 15)
    private Long type;

}
