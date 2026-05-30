package com.ut.nlSystemAPi.model.request.Login.BillReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayBillReturnWithPbsRequest {

    @ApiModelProperty(position = 1)
    private Long billReturnId;

    @ApiModelProperty(position = 2)
    private String applyDate;

    @ApiModelProperty(position = 3)
    private List<PayBillReturnWithPbsDetailRequest> details;

}
