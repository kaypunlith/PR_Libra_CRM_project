package com.ut.nlSystemAPi.model.request.Login.LandedCost;

import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillDetailRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LandedCostRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long paidToId;

    @ApiModelProperty(position = 5)
    private Long landedCostTypeId;

    @ApiModelProperty(position = 6)
    private String reference;

    @ApiModelProperty(position = 7)
    private Long supplyId;

    @ApiModelProperty(position = 9)
    private Long apId;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Double totalAmount;

    @ApiModelProperty(position = 11)
    private List<Long> purchaseBills;

    @ApiModelProperty(position = 24)
    private List<LandedCostDetailRequest> details;

}
