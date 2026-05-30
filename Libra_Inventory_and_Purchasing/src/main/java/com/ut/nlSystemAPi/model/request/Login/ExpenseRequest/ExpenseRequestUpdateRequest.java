package com.ut.nlSystemAPi.model.request.Login.ExpenseRequest;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExpenseRequestUpdateRequest extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String erDate;

    @ApiModelProperty(position = 4)
    private Long departmentId;

    @ApiModelProperty(position = 5)
    private String expectedErDate;

    @ApiModelProperty(position = 6)
    private Long isCashAdvance;

    @ApiModelProperty(position = 7)
    private RefDoc refDoc;

    @ApiModelProperty(position = 8)
    private String note;

    @ApiModelProperty(position = 9)
    private Long vendorId;

    @ApiModelProperty(position = 10)
    private Double totalAmount;

    @ApiModelProperty(position = 11)
    private Long vatSettingId;

    @ApiModelProperty(position = 13)
    private Double totalVat;

    @ApiModelProperty(position = 14)
    private Long purchaseType;

    @ApiModelProperty(position = 14)
    private Long purchaseId;

    @ApiModelProperty(position = 15)
    private List<PvRequestDetailsRequest> details;

}
