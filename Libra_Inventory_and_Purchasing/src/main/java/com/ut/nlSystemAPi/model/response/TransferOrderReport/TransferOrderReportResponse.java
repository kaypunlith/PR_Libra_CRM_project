package com.ut.nlSystemAPi.model.response.TransferOrderReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class TransferOrderReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String toNumber;

    @ApiModelProperty(position = 1)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 1)
    private String fromWarehouseName;

    @ApiModelProperty(position = 2)
    private Long toWarehouseId;

    @ApiModelProperty(position = 2)
    private String toWarehouseName;

    @ApiModelProperty(position = 3)
    private String orderDate;

    @ApiModelProperty(position = 4)
    private String fulfillmentDate;

    @ApiModelProperty(position = 5)
    private Long status;
}
