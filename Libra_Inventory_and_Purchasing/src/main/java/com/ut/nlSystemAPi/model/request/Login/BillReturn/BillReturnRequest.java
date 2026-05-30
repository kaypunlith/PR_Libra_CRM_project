package com.ut.nlSystemAPi.model.request.Login.BillReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BillReturnRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 3)
    private Long warehouseId;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 5)
    private Long purchaseBillId;

    @ApiModelProperty(position = 10)
    private String date;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 15)
    private Long apId;

    @ApiModelProperty(position = 19)
    private Double subTotal;

    @ApiModelProperty(position = 21)
    private Long vatSettingId;

    @ApiModelProperty(position = 22)
    private Double total;

    @ApiModelProperty(position = 24)
    private List<BillReturnDetailRequest> details;

}
