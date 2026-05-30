package com.ut.nlSystemAPi.model.response.Voucher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VoucherResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private String branchName;

    @ApiModelProperty(position = 4)
    private String voucherCode;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 6)
    private String date;

    @ApiModelProperty(position = 7)
    private String startDate;

    @ApiModelProperty(position = 8)
    private String endDate;

    @ApiModelProperty(position = 9)
    private Integer userType;

    @ApiModelProperty(position = 10)
    private Integer voucherType;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 12)
    private Double discountAmount;

    @ApiModelProperty(position = 13)
    private Double discountPercent;

    @ApiModelProperty(position = 14)
    private Integer status;

    @ApiModelProperty(position = 15)
    private String approved;

    @ApiModelProperty(position = 16)
    private String approvedBy;

    @ApiModelProperty(position = 17)
    private String created;

    @ApiModelProperty(position = 18)
    private String createdBy;

    @ApiModelProperty(position = 19)
    private String modified;

    @ApiModelProperty(position = 20)
    private String modifiedBy;
}
