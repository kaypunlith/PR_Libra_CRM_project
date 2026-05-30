package com.ut.nlSystemAPi.model.response.Delivery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long saleOrderId;

    @ApiModelProperty(position = 2)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 3)
    private Long salesInvoiceDetailId;

    @ApiModelProperty(position = 5)
    private String invoiceDate;

    @ApiModelProperty(position = 5)
    private String invoiceCode;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 5)
    private String productName;

    @ApiModelProperty(position = 6)
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private String warehouseName;

    @ApiModelProperty(position = 6)
    private Long locationId;

    @ApiModelProperty(position = 7)
    private String locationName;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 9)
    private String uomName;

    @ApiModelProperty(position = 10)
    private String expiredDate;

    @ApiModelProperty(position = 11)
    private Long qty;
}
