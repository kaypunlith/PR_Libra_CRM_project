package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationRequest {

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String moduleCode;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private Long currencyId;

    @ApiModelProperty(position = 7)
    private Long priceTypeId;

    @ApiModelProperty(position = 7)
    private Long vatSettingId;

    @ApiModelProperty(position = 10)
    private Long organizationContactId;

    @ApiModelProperty(position = 12)
    private Double vatPercent;

    @ApiModelProperty(position = 12)
    private Double totalVat;

    @ApiModelProperty(position = 12)
    private Double discountAmount;

    @ApiModelProperty(position = 12)
    private Double discountPercent;

    @ApiModelProperty(position = 12)
    private Double subTotal;

    @ApiModelProperty(position = 12)
    private Double marginPercent;

    @ApiModelProperty(position = 17)
    private Long shareSaveOption;

    @ApiModelProperty(position = 18)
    private Long shareOption;

    @ApiModelProperty(position = 19)
    private List<Long> shareUser;

    @ApiModelProperty(position = 20)
    private List<Long> shareExceptUser;

    @ApiModelProperty(position = 21)
    private String note;

    @ApiModelProperty(position = 17)
    private Integer isApply;

    @ApiModelProperty(position = 23)
    private Integer isNoneVat;

    @ApiModelProperty(position = 26)
    private List<QuotationTermConditionRequest> termConditions;

    @ApiModelProperty(position = 26)
    private List<QuotationDetailRequest> details;

}
