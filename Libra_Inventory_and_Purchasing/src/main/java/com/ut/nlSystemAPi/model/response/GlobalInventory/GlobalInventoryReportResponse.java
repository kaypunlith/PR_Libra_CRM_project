package com.ut.nlSystemAPi.model.response.GlobalInventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GlobalInventoryReportResponse {

    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 2)
    private String parentName;

    @ApiModelProperty(position = 3)
    private Double subTotal;

    @ApiModelProperty(position = 4)
    private Double grandTotal;

    @ApiModelProperty(position = 5)
    private List<GlobalInventoryReportDetailResponse> details;

}
