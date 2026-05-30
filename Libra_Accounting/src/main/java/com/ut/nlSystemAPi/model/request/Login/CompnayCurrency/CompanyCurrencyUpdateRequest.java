package com.ut.nlSystemAPi.model.request.Login.CompnayCurrency;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyCurrencyUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 11)
    private Long currencyId;

    @ApiModelProperty(position = 12)
    private Long branchId;

}
