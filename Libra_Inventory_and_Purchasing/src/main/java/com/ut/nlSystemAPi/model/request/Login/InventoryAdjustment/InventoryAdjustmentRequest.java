package com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class InventoryAdjustmentRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 4)
    private String code;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private Long depositTo;

    @ApiModelProperty(position = 6)
    private Long productGroupId;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 8)
    private Long cpType;

    @ApiModelProperty(position = 9)
    private Long type;

    @ApiModelProperty(position = 10)
    private List<InventoryAdjustmentDetailRequest> details;

}
