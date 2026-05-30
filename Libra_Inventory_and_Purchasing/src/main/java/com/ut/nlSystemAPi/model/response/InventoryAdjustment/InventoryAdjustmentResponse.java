package com.ut.nlSystemAPi.model.response.InventoryAdjustment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class InventoryAdjustmentResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String adj;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 3)
    private String warehouseName;

    @ApiModelProperty(position = 4)
    private Long adjustmentId;

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
    private Long depositTo;

    @ApiModelProperty(position = 7)
    private Long status;

    @ApiModelProperty(position = 7)
    private Long type;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 9)
    private String createdBy;

    @ApiModelProperty(position = 10)
    private String modified;

    @ApiModelProperty(position = 11)
    private String modifiedBy;

    @ApiModelProperty(position = 12)
    private List<InventoryAdjustmentDetailsResponse> details;

}
