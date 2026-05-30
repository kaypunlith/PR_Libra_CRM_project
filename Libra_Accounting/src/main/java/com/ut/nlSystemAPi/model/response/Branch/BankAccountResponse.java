package com.ut.nlSystemAPi.model.response.Branch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BankAccountResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String accountNumber;

    @ApiModelProperty(position = 3)
    private String accountHolder;

    @ApiModelProperty(position = 4)
    private String bank;

    @ApiModelProperty(position = 5)
    private String address;


}
