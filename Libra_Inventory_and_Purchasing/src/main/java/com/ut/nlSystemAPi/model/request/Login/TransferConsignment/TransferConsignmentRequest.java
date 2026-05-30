package com.ut.nlSystemAPi.model.request.Login.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TransferConsignmentRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String toNumber;

    @ApiModelProperty(position = 3)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 4)
    private Long toWarehouseId;

    @ApiModelProperty(position = 5)
    private String toDate;

    @ApiModelProperty(position = 6)
    private String fulfillmentDate;

    @ApiModelProperty(position = 7)
    private String memo;

    @ApiModelProperty(position = 9)
    private Long type;

    @ApiModelProperty(position = 10)
    private Long toType;

    @ApiModelProperty(position = 10)
    private Long requestStockId;

    @ApiModelProperty(position = 12)
    private List<TransferConsignmentDetailRequest> details;

}
