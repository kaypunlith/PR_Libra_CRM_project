package com.ut.nlSystemAPi.model.request.Login.Reconcile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReconcileRequest {

    @ApiModelProperty(position = 1)
    private List<Long> ids;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private String dateFrom;

    @ApiModelProperty(position = 3)
    private String dateTo;

    @ApiModelProperty(position = 4)
    private Long chartAccountId;

    @ApiModelProperty(position = 5)
    private Double serviceChargeAmount;

    @ApiModelProperty(position = 6)
    private String serviceChargeDate;

    @ApiModelProperty(position = 7)
    private Long serviceChargeAccountId;

    @ApiModelProperty(position = 8)
    private Long serviceChargeClassId;


    @ApiModelProperty(position = 9)
    private Double interestedEarnedAmount;

    @ApiModelProperty(position = 10)
    private String interestedEarnedDate;

    @ApiModelProperty(position = 11)
    private Long interestedEarnedAccountId;

    @ApiModelProperty(position = 12)
    private Long interestedEarnedClassId;


    @ApiModelProperty(position = 13)
    private Double diff;

    @ApiModelProperty(position = 14)
    private Long diffAccountId;

    @ApiModelProperty(position = 15)
    private Long diffClassId;


    @ApiModelProperty(position = 16, hidden = true)
    private Double endingBalance;

    @ApiModelProperty(position = 17, hidden = true)
    private Double clearedBalance;
}
