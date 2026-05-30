package com.ut.nlSystemAPi.model.response.BillReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BillReturnResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long currencyCenterId;

    @ApiModelProperty(position = 4)
    private String currencyCenterName;

    @ApiModelProperty(position = 4)
    private Long purchaseBillId;

    @ApiModelProperty(position = 4)
    private String purchaseBillCode;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 5)
    private Long vendorId;

    @ApiModelProperty(position = 6)
    private String vendorName;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 8)
    private String warehouseName;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String locationName;

    @ApiModelProperty(position = 11)
    private String orderDate;

    @ApiModelProperty(position = 12)
    private String note;

    @ApiModelProperty(position = 13)
    private String prCode;

    @ApiModelProperty(position = 14)
    private Long apId;

    @ApiModelProperty(position = 15)
    private String apName;

    @ApiModelProperty(position = 16)
    private Long vatChartAccountId;

    @ApiModelProperty(position = 16)
    private String vatChartAccountName;

    @ApiModelProperty(position = 17)
    private Long vatCalculate;

    @ApiModelProperty(position = 18)
    private Double subTotal;

    @ApiModelProperty(position = 19)
    private Long vatSettingId;

    @ApiModelProperty(position = 20)
    private String vatSettingName;

    @ApiModelProperty(position = 21)
    private Double total;

    @ApiModelProperty(position = 22)
    private Double totalVat;

    @ApiModelProperty(position = 23)
    private Double vatPercentage;

    @ApiModelProperty(position = 24)
    private Double balance;

    @ApiModelProperty(position = 25)
    private String created;

    @ApiModelProperty(position = 26)
    private String createdBy;

    @ApiModelProperty(position = 27)
    private String modified;

    @ApiModelProperty(position = 28)
    private String modifiedBy;

    @ApiModelProperty(position = 29)
    private Long status;

    @ApiModelProperty(position = 30)
    private List<BillReturnDetailResponse> details;

}
