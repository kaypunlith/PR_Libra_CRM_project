package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignment extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long requestStockId;

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

    @ApiModelProperty(position = 8)
    private Long type;

    @ApiModelProperty(position = 8)
    private Long toType;

}
