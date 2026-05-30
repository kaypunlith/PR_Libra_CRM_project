package com.ut.nlSystemAPi.model.request.Login.InventorySample;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InventorySampleRequestUpdate {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String reference;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 6)
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private Long pgroupId;

    @ApiModelProperty(position = 8)
    List<InventorySampleRequestDetail> inventorySampleRequestDetail;
}
