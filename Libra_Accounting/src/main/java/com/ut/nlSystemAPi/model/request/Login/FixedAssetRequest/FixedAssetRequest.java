package com.ut.nlSystemAPi.model.request.Login.FixedAssetRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FixedAssetRequest {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private Long locationId;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private String fixedAssetCode;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private String purchaseOrderNumber;

    @ApiModelProperty(position = 8)
    private String serialNumber;

    @ApiModelProperty(position = 9)
    private String date;

    @ApiModelProperty(position = 10)
    private String warrantyExpires;

    @ApiModelProperty(position = 11)
    private Long assetAccount;

    @ApiModelProperty(position = 12)
    private Double cost;

    @ApiModelProperty(position = 13)
    private Double costRemain;

    @ApiModelProperty(position = 14)
    private String photo;

    @ApiModelProperty(position = 15)
    private Long isDepre;

    @ApiModelProperty(position = 16)
    private Long accumulatedDepartment;

    @ApiModelProperty(position = 17)
    private Long deprExpense;

    @ApiModelProperty(position = 18)
    private String deprMethod;

    @ApiModelProperty(position = 19)
    private Double assetLife;

    @ApiModelProperty(position = 20)
    private Double salvageValue;

    @ApiModelProperty(position = 21)
    private Double businessUsePercentage;

    @ApiModelProperty(position = 22)
    private String description;

    @ApiModelProperty(position = 23)
    private String photoName;

    @ApiModelProperty(position = 24, hidden = true)
    private String created;

    @ApiModelProperty(position = 25, hidden = true)
    private String modified;

    @ApiModelProperty(position = 26, hidden = true)
    private String modifiedBy;

    @ApiModelProperty(position = 27, hidden = true)
    private Boolean isInUsed;

    @ApiModelProperty(position = 28, hidden = true)
    private Integer type;

    @ApiModelProperty(position = 29)
    private List<FixedAssetFileRequest> file;
}
