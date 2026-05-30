package com.ut.nlSystemAPi.model.request.Login.BillReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayBillReturnWithPbsDetailRequest {

    @ApiModelProperty(position = 3)
    private Long purchaseBillId;

    @ApiModelProperty(position = 5)
    private Double paid;

}
