package com.ut.nlSystemAPi.model.response.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class CreditMemoResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 2)
    private String companyTelephone;

    @ApiModelProperty(position = 2)
    private String companyAddress;

    @ApiModelProperty(position = 2)
    private String companyWebsite;

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 3)
    private String warehouseName;

    @ApiModelProperty(position = 2)
    private String cmNo;

    @ApiModelProperty(position = 3)
    private String cmDate;

    @ApiModelProperty(position = 4)
    private Long invoiceId;

    @ApiModelProperty(position = 4)
    private String invoiceCode;

    @ApiModelProperty(position = 5)
    private String invoiceDate;

    @ApiModelProperty(position = 5)
    private Long organizationId;

    @ApiModelProperty(position = 6)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private String organizationCode;

    @ApiModelProperty(position = 5)
    private Long priceTypeId;

    @ApiModelProperty(position = 6)
    private String priceTypeName;

    @ApiModelProperty(position = 7)
    private Double subTotal;

    @ApiModelProperty(position = 7)
    private Double totalAmount;

    @ApiModelProperty(position = 8)
    private Double balance;

    @ApiModelProperty(position = 9)
    private Integer status;

    @ApiModelProperty(position = 14)
    private Long locationId;

    @ApiModelProperty(position = 15)
    private String locationName;

    @ApiModelProperty(position = 16)
    private Long cmTermId;

    @ApiModelProperty(position = 17)
    private String cmTermName;

    @ApiModelProperty(position = 18)
    private Long chartAccountId;

    @ApiModelProperty(position = 18)
    private String chartAccountName;

    @ApiModelProperty(position = 19)
    private Long vatId;

    @ApiModelProperty(position = 19)
    private String vatName;

    @ApiModelProperty(position = 19)
    private Double vatPercent;

    @ApiModelProperty(position = 20)
    private Double discountAmount;

    @ApiModelProperty(position = 20)
    private Double discountPercent;

    @ApiModelProperty(position = 21)
    private Double markUp;

    @ApiModelProperty(position = 22)
    private Double totalVat;

    @ApiModelProperty(position = 23)
    private Long currencyCenterId;

    @ApiModelProperty(position = 23)
    private String currencyCenterName;

    @ApiModelProperty(position = 23)
    private String currencyCenterSymbol;

    @ApiModelProperty(position = 24)
    private String note;

    @ApiModelProperty(position = 25)
    private List<CreditMemoDetailResponse> details;
}
