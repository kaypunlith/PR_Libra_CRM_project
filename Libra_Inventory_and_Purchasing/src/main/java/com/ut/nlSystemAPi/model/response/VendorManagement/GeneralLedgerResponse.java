package com.ut.nlSystemAPi.model.response.VendorManagement;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GeneralLedgerResponse extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String vendorCode;

    @ApiModelProperty(position = 3)
    private String vendorName;

    @ApiModelProperty(position = 4)
    private Long officeTelephone;

    @ApiModelProperty(position = 4)
    private Long otherTelephone;

    @ApiModelProperty(position = 5)
    private Long otherNumber;

    @ApiModelProperty(position = 6)
    private Long paymentTermId;

    @ApiModelProperty(position = 7)
    private String emailAddress;

    @ApiModelProperty(position = 8)
    private Long Balance;

    @ApiModelProperty(position = 9)
    private Long faxNumber;

    @ApiModelProperty(position = 9)
    private Long countryId;

    @ApiModelProperty(position = 9)
    private String photo;

    @ApiModelProperty(position = 9)
    private String paymentTermName;

}
