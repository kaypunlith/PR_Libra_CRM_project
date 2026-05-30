package com.ut.nlSystemAPi.model.response.VendorManagement;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GeneralLedgerDetailResponse extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long generalLedgerId;

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 2)
    private Double credit;

    @ApiModelProperty(position = 3)
    private Double debit;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 4)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private Long vendorId;

    @ApiModelProperty(position = 6)
    private Long employeeId;

    @ApiModelProperty(position = 8)
    private Long companyId;

}
