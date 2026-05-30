package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturn extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long updatedId;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private Long purchaseBillId;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String date;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 13)
    private String prCode;

    @ApiModelProperty(position = 15)
    private Long apId;

    @ApiModelProperty(position = 16)
    private Long vatChartAccountId;

    @ApiModelProperty(position = 16)
    private Long currencyCenterId;

    @ApiModelProperty(position = 16)
    private Long vatCalculate;

    @ApiModelProperty(position = 19)
    private Double subTotal;

    @ApiModelProperty(position = 21)
    private Long vatSettingId;

    @ApiModelProperty(position = 22)
    private Double total;

    @ApiModelProperty(position = 22)
    private Double totalVat;

    @ApiModelProperty(position = 23)
    private Double vatPercentage;

    @ApiModelProperty(position = 24)
    private Double balance;

}
