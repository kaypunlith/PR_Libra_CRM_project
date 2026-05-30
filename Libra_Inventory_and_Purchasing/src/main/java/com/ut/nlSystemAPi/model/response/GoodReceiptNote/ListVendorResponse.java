package com.ut.nlSystemAPi.model.response.GoodReceiptNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListVendorResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String vendorCode;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 3)
    private String address;

    @ApiModelProperty(position = 1)
    private String workTelephone;

    @ApiModelProperty(position = 1)
    private String NetDay;
}
