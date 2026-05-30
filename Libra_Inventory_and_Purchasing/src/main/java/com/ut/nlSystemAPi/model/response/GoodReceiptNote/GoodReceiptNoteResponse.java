package com.ut.nlSystemAPi.model.response.GoodReceiptNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoodReceiptNoteResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long isNotAllowDisapproved;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private String warehouseName;

    @ApiModelProperty(position = 5)
    private Long warehouseId;

    @ApiModelProperty(position = 6)
    private String locationName;

    @ApiModelProperty(position = 7)
    private Long locationId;

    @ApiModelProperty(position = 7)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 8)
    private String purchaseOrderCode;

    @ApiModelProperty(position = 8)
    private String purchaseOrderDate;

    @ApiModelProperty(position = 8)
    private Long purchaseBillId;

    @ApiModelProperty(position = 9)
    private String purchaseBillCode;

    @ApiModelProperty(position = 10)
    private String vendorName;

    @ApiModelProperty(position = 11)
    private Long vendorId;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 12)
    private String approved;

    @ApiModelProperty(position = 13)
    private String approvedBy;

    @ApiModelProperty(position = 12)
    private String disapproved;

    @ApiModelProperty(position = 13)
    private String disapprovedBy;

    @ApiModelProperty(position = 14)
    private Long status;

    @ApiModelProperty(position = 15)
    private List<GoodReceiptNoteDetailResponse> goodReceiptNoteDetailResponses;

}
