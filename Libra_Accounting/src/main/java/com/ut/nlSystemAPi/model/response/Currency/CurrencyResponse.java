package com.ut.nlSystemAPi.model.response.Currency;

import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CurrencyResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String symbol;

    @ApiModelProperty(position = 4)
    private String createdDate;

    @ApiModelProperty(position = 15)
    private String modifiedDate;

    @ApiModelProperty(position = 16)
    private String createdBy;

    @ApiModelProperty(position = 17)
    private String modifiedBy;

    @ApiModelProperty(position = 18)
    private Long status;

}
