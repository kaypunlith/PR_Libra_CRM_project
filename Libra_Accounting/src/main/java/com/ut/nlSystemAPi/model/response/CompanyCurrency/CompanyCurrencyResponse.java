package com.ut.nlSystemAPi.model.response.CompanyCurrency;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyCurrencyResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private String currencyFrom;

    @ApiModelProperty(position = 5)
    private Long currencyToId;

    @ApiModelProperty(position = 6)
    private String currencyTo;

    @ApiModelProperty(position = 7)
    private Long isPosDefault;

    @ApiModelProperty(position = 8)
    private Long branchId;

    @ApiModelProperty(position = 9)
    private String branchName;

    @ApiModelProperty(position = 8)
    private String createdDate;

    @ApiModelProperty(position = 9)
    private String modifiedDate;

    @ApiModelProperty(position = 10)
    private String createdBy;

    @ApiModelProperty(position = 11)
    private String modifiedBy;

    @ApiModelProperty(position = 12)
    private Long status;

}
