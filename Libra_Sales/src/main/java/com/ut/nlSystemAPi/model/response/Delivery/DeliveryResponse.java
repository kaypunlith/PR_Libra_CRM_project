package com.ut.nlSystemAPi.model.response.Delivery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String code;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 6)
    private String invoiceCode;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 8)
    private String warehouseName;

    @ApiModelProperty(position = 9)
    private Long customerGroupId;

    @ApiModelProperty(position = 10)
    private String customerGroupName;

    @ApiModelProperty(position = 11)
    private Long customerId;

    @ApiModelProperty(position = 12)
    private String customerName;

    @ApiModelProperty(position = 13)
    private Long deliveryId;

    @ApiModelProperty(position = 14)
    private String deliveryName;

    @ApiModelProperty(position = 13)
    private Integer status;

    @ApiModelProperty(position = 14)
    private String created;

    @ApiModelProperty(position = 15)
    private String createdBy;

    @ApiModelProperty(position = 16)
    private String modified;

    @ApiModelProperty(position = 17)
    private String modifiedBy;

    @ApiModelProperty(position = 17)
    private Long isApprove;

    @ApiModelProperty(position = 16)
    private String approved;

    @ApiModelProperty(position = 17)
    private String approvedBy;

    @ApiModelProperty(position = 18)
    private List<DeliveryDetailResponse> details;
}
