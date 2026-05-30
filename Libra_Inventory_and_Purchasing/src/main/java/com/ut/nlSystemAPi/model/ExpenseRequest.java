package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.RefDoc;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpenseRequest extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private String code;

    @ApiModelProperty(position = 5)
    private String erDate;

    @ApiModelProperty(position = 6)
    private Long departmentId;

    @ApiModelProperty(position = 7)
    private String expectedErDate;

    @ApiModelProperty(position = 8)
    private Long isCashAdvance;

    @ApiModelProperty(position = 9)
    private String refDocName;

    @ApiModelProperty(position = 9)
    private String refDocUrl;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Double totalAmount;

    @ApiModelProperty(position = 12)
    private Long vatSettingId;

    @ApiModelProperty(position = 14)
    private Double totalVat;

    @ApiModelProperty(position = 15)
    private Long purchaseType;

    @ApiModelProperty(position = 15)
    private Long purchaseId;

    @ApiModelProperty(position = 16)
    private Long isClose;
}
