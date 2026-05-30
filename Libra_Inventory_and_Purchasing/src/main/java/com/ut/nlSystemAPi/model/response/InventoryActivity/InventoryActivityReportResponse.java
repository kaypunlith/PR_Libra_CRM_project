package com.ut.nlSystemAPi.model.response.InventoryActivity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class InventoryActivityReportResponse {

    @ApiModelProperty(position = 1)
    private Long itemId;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 2)
    private String uomName;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long qty;

    @ApiModelProperty(position = 3)
    private String warehouseName;

    @ApiModelProperty(position = 3)
    private String locationName;

    @ApiModelProperty(position = 4)
    private String adjustment;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private Long productGroupId;

    @ApiModelProperty(position = 6)
    private String productGroupName;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 7)
    private Long status;

    @ApiModelProperty(position = 7)
    private Double totalQty;

    @ApiModelProperty(position = 7)
    private Double totalBeginning;

    @ApiModelProperty(position = 7)
    private Double totalInOut;

    @ApiModelProperty(position = 7)
    private Double totalEnding;

    @ApiModelProperty(position = 7)
    private Double grandTotal;

    @ApiModelProperty(position = 12)
    private List<InventoryActivityReportDetailResponse> details;

}
