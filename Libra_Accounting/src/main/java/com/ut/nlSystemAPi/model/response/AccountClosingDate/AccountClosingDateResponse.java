package com.ut.nlSystemAPi.model.response.AccountClosingDate;

import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountClosingDateResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

}
