package com.ut.nlSystemAPi.model.request.Login.Branch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BanksAccountRequest {

    @ApiModelProperty(position = 3)
    private String accountNumber ;

    @ApiModelProperty(position = 4)
    private String accountHolder;

    @ApiModelProperty(position = 5)
    private String bank;

    @ApiModelProperty(position = 6)
    private String address;
}
