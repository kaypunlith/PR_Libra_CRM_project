package com.ut.nlSystemAPi.model.request.Voucher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VoucherRequest {

    @ApiModelProperty(position = 2)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private String voucherCode;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private String startDate;

    @ApiModelProperty(position = 7)
    private String endDate;

    @ApiModelProperty(position = 8)
    private Integer userType;

    @ApiModelProperty(position = 9)
    private Integer voucherType;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Double discountAmount;

    @ApiModelProperty(position = 12)
    private Double discountPercent;
}
