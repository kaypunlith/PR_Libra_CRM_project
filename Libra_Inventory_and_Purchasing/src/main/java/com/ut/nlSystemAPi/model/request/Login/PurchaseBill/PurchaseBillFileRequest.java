package com.ut.nlSystemAPi.model.request.Login.PurchaseBill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseBillFileRequest {

    @ApiModelProperty(position = 1)
    private String url;

    @ApiModelProperty(position = 2)
    private String name;
}
