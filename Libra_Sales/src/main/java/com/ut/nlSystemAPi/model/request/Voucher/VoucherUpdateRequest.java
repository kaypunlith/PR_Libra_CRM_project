package com.ut.nlSystemAPi.model.request.Voucher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VoucherUpdateRequest extends VoucherRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
