package com.ut.nlSystemAPi.model.response.LandedCost;

import com.ut.nlSystemAPi.model.LandedCostPurchaseBill;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LandedCostResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 2)
    private Long vendorId;

    @ApiModelProperty(position = 2)
    private String vendorName;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long paidToId;

    @ApiModelProperty(position = 4)
    private String paidToName;

    @ApiModelProperty(position = 4)
    private Long landedCostTypeId;

    @ApiModelProperty(position = 4)
    private String landedCostTypeName;

    @ApiModelProperty(position = 6)
    private String reference;

    @ApiModelProperty(position = 7)
    private Long supplyId;

    @ApiModelProperty(position = 7)
    private String supplyName;

    @ApiModelProperty(position = 9)
    private Long apId;

    @ApiModelProperty(position = 9)
    private String apName;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Double totalAmount;

    @ApiModelProperty(position = 11)
    private Double balance;

    @ApiModelProperty(position = 12)
    private Long status;

    @ApiModelProperty(position = 13)
    private String created;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 15)
    private String modified;

    @ApiModelProperty(position = 16)
    private String modifiedBy;

    @ApiModelProperty(position = 17)
    private List<LandedCostPurchaseBillResponse> purchaseBills;

    @ApiModelProperty(position = 17)
    private List<LandedCostDetailResponse> details;
}
