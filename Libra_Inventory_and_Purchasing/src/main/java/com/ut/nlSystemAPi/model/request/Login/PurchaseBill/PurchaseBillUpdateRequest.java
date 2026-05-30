package com.ut.nlSystemAPi.model.request.Login.PurchaseBill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PurchaseBillUpdateRequest extends PurchaseBillRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
