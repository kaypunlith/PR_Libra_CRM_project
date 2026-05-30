package com.ut.nlSystemAPi.model.request.SaleOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaleOrderRequest {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Integer isUpdate;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String moduleCode;

    @ApiModelProperty(position = 3)
    private List<Long> quotations;

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

    @ApiModelProperty(position = 10)
    private String organizationPoNo;

    @ApiModelProperty(position = 10)
    private String organizationPoFile;

    @ApiModelProperty(position = 10)
    private String deliveryDate;

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

    @ApiModelProperty(position = 21)
    private String note;

    @ApiModelProperty(position = 17)
    private Integer isApply;

    @ApiModelProperty(position = 23)
    private Integer isRequiredPo;

    @ApiModelProperty(position = 23)
    private Integer isNoneVat;

    @ApiModelProperty(position = 23)
    private Integer isWso;

    @ApiModelProperty(position = 26)
    private List<SaleOrderTermConditionRequest> termConditions;

    @ApiModelProperty(position = 26)
    private List<SaleOrderDetailRequest> details;

}
