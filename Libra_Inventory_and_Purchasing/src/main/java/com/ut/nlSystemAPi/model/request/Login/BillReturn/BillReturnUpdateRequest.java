package com.ut.nlSystemAPi.model.request.Login.BillReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BillReturnUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

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
