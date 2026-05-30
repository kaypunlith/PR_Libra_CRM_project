package com.ut.nlSystemAPi.model.request.Login.VendorContact;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorContactRequest extends BaseModel {

    @ApiModelProperty(position = 2)
    private Long vendorId;

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

}
