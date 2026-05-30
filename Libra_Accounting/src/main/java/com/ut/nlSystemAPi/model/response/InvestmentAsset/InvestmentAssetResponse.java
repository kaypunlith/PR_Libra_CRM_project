package com.ut.nlSystemAPi.model.response.InvestmentAsset;

import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleResponseDetails;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetFileResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InvestmentAssetResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 4)
    private Long branchId;

    @ApiModelProperty(position = 5)
    private String branchName;

    @ApiModelProperty(position = 5)
    private Long locationId;

    @ApiModelProperty(position = 6)
    private String locationName;

    @ApiModelProperty(position = 7)
    private Long vendorId;

    @ApiModelProperty(position = 7)
    private Double depreciation;

    @ApiModelProperty(position = 8)
    private String vendorName;

    @ApiModelProperty(position = 9)
    private String assetCode;

    @ApiModelProperty(position = 10)
    private String assetName;

    @ApiModelProperty(position = 11)
    private String purchaseOrderNumber;

    @ApiModelProperty(position = 12)
    private String serialNumber;

    @ApiModelProperty(position = 13)
    private String warrantyExpires;

    @ApiModelProperty(position = 14)
    private Long assetAccountId;

    @ApiModelProperty(position = 15)
    private String assetAccountName;

    @ApiModelProperty(position = 16)
    private Double cost;

    @ApiModelProperty(position = 17)
    private Long accumulatedDepartmentId;

    @ApiModelProperty(position = 18)
    private String accumulatedDepartmentName;

    @ApiModelProperty(position = 19)
    private String deprMethod;

    @ApiModelProperty(position = 20)
    private Long deprExpenseId;

    @ApiModelProperty(position = 21)
    private String deprExpenseName;

    @ApiModelProperty(position = 22)
    private String depreMethodName;

    @ApiModelProperty(position = 23)
    private Long assetLife;

    @ApiModelProperty(position = 24)
    private Double costRemain;

    @ApiModelProperty(position = 25)
    private Double salvageValue;

    @ApiModelProperty(position = 27)
    private Double depreciationDayAmount;

    @ApiModelProperty(position = 26)
    private Double businessUsePercentage;

    @ApiModelProperty(position = 27)
    private Integer appreciation;

    @ApiModelProperty(position = 28)
    private String photoName;

    @ApiModelProperty(position = 29)
    private String photo;

    @ApiModelProperty(position = 30)
    private String createdBy;

    @ApiModelProperty(position = 31)
    private String modifiedBy;

    @ApiModelProperty(position = 32)
    private String description;

    @ApiModelProperty(position = 33)
    private Boolean isInUsed;

    @ApiModelProperty(position = 34)
    private Boolean isDepre;

    @ApiModelProperty(position = 35)
    private String created;

    @ApiModelProperty(position = 36)
    private String modified;

    @ApiModelProperty(position = 37)
    private List<InvestmentAssetFileResponse> file;

}
