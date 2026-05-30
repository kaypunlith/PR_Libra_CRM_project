package com.ut.nlSystemAPi.model.request.Login.PayBills;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayBillFileRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 3)
    private String url;

}
