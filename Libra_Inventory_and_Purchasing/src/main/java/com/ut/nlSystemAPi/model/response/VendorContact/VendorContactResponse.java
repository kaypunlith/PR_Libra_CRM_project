package com.ut.nlSystemAPi.model.response.VendorContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorContactResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long vendorId;

    @ApiModelProperty(position = 2)
    private String vendorName;

    @ApiModelProperty(position = 3)
    private String contactName;

    @ApiModelProperty(position = 4)
    private String sex;

    @ApiModelProperty(position = 5)
    private String contactTelephone;

    @ApiModelProperty(position = 6)
    private String contactEmail;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 9)
    private String createdBy;

    @ApiModelProperty(position = 10)
    private String modified;

    @ApiModelProperty(position = 11)
    private String modifiedBy;

}
