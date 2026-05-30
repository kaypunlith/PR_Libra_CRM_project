package com.ut.nlSystemAPi.model.response.InventorySample;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InventorySampleResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String adjNO;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private String companyName;

    @ApiModelProperty(position = 6)
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private String warehouseName;

    @ApiModelProperty(position = 8)
    private Long productGroupId;

    @ApiModelProperty(position = 9)
    private String productGroupName;

    @ApiModelProperty(position = 10)
    private String description;

    @ApiModelProperty(position = 11)
    private String created;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private Long status;

    @ApiModelProperty(position = 14)
    List<InventorySampleProductResponseDetail> inventorySampleProductRepsonseDetailList;
}
