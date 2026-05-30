package com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountClosingDateRequest {

    @ApiModelProperty(position = 4)
    private String date;
}
