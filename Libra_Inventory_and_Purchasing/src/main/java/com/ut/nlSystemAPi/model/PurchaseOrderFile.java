package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseOrderFile {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 3)
    private String fileUrl;

    @ApiModelProperty(position = 4)
    private String fileName;
}
